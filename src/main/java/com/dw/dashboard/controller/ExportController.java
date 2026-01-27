package com.dw.dashboard.controller;

import com.dw.dashboard.common.Result;
import com.dw.dashboard.dto.request.QueryExecuteRequest;
import com.dw.dashboard.dto.response.QueryResultResponse;
import com.dw.dashboard.service.IDashboardExecuteService;
import com.dw.dashboard.service.IExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 导出控制器
 *
 * @author DW Team
 * @since 2026-01-27
 */
@Slf4j
@Tag(name = "导出管理", description = "报表数据导出相关接口")
@RestController
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final IDashboardExecuteService dashboardExecuteService;
    private final IExportService exportService;

    /**
     * 导出为Excel
     *
     * @param request 查询请求
     * @return Excel文件
     */
    @Operation(summary = "导出为Excel", description = "将查询结果导出为Excel文件")
    @PostMapping("/excel")
    public ResponseEntity<byte[]> exportToExcel(@Valid @RequestBody QueryExecuteRequest request) {
        log.info("导出Excel: queryId={}", request.getQueryId());

        // 1. 执行查询
        QueryResultResponse queryResult = dashboardExecuteService.executeQuery(request);

        // 2. 导出为Excel
        byte[] excelData = exportService.exportToExcel(queryResult.getColumns(), queryResult.getData());

        // 3. 设置响应头
        String filename = "export_" + System.currentTimeMillis() + ".xlsx";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFilename);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelData);
    }

    /**
     * 导出为CSV
     *
     * @param request 查询请求
     * @return CSV文件
     */
    @Operation(summary = "导出为CSV", description = "将查询结果导出为CSV文件")
    @PostMapping("/csv")
    public ResponseEntity<byte[]> exportToCsv(@Valid @RequestBody QueryExecuteRequest request) {
        log.info("导出CSV: queryId={}", request.getQueryId());

        // 1. 执行查询
        QueryResultResponse queryResult = dashboardExecuteService.executeQuery(request);

        // 2. 导出为CSV
        byte[] csvData = exportService.exportToCsv(queryResult.getColumns(), queryResult.getData());

        // 3. 设置响应头
        String filename = "export_" + System.currentTimeMillis() + ".csv";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment", encodedFilename);

        return ResponseEntity.ok()
                .headers(headers)
                .body(csvData);
    }

    /**
     * 导出为PDF
     *
     * @param request 查询请求
     * @return PDF文件
     */
    @Operation(summary = "导出为PDF", description = "将查询结果导出为PDF文件")
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> exportToPdf(@Valid @RequestBody QueryExecuteRequest request,
                                               @Parameter(description = "报表标题") @RequestParam(required = false) String title) {
        log.info("导出PDF: queryId={}", request.getQueryId());

        // 1. 执行查询
        QueryResultResponse queryResult = dashboardExecuteService.executeQuery(request);

        // 2. 导出为PDF
        String pdfTitle = title != null ? title : "数据报表";
        byte[] pdfData = exportService.exportToPdf(pdfTitle, queryResult.getColumns(), queryResult.getData());

        // 3. 设置响应头
        String filename = "export_" + System.currentTimeMillis() + ".pdf";
        String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", encodedFilename);

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfData);
    }

}
