package com.dw.dashboard.service.impl;

import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.exception.BusinessException;
import com.dw.dashboard.service.IExportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * 导出服务实现
 *
 * @author DW Team
 * @since 2026-01-27
 */
@Slf4j
@Service
public class ExportServiceImpl implements IExportService {

    @Override
    public byte[] exportToExcel(List<String> headers, List<Map<String, Object>> data) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("数据");

            // 创建表头样式
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // 创建表头
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
                cell.setCellStyle(headerStyle);
            }

            // 填充数据
            int rowNum = 1;
            for (Map<String, Object> row : data) {
                Row dataRow = sheet.createRow(rowNum++);
                int colNum = 0;
                for (String header : headers) {
                    Cell cell = dataRow.createCell(colNum++);
                    Object value = row.get(header);
                    if (value != null) {
                        cell.setCellValue(value.toString());
                    }
                }
            }

            // 自动调整列宽
            for (int i = 0; i < headers.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            log.info("导出Excel成功: 行数={}", data.size());
            return out.toByteArray();

        } catch (Exception e) {
            log.error("导出Excel失败: {}", e.getMessage());
            throw new BusinessException(ResultCode.INTERNAL_ERROR, "导出Excel失败");
        }
    }

    @Override
    public byte[] exportToCsv(List<String> headers, List<Map<String, Object>> data) {
        try {
            StringBuilder csv = new StringBuilder();

            // 添加表头
            csv.append(String.join(",", headers)).append("\n");

            // 添加数据
            for (Map<String, Object> row : data) {
                StringBuilder line = new StringBuilder();
                for (int i = 0; i < headers.size(); i++) {
                    if (i > 0) {
                        line.append(",");
                    }
                    Object value = row.get(headers.get(i));
                    if (value != null) {
                        String strValue = value.toString();
                        // 如果包含逗号或引号，需要用引号包裹
                        if (strValue.contains(",") || strValue.contains("\"")) {
                            strValue = "\"" + strValue.replace("\"", "\"\"") + "\"";
                        }
                        line.append(strValue);
                    }
                }
                csv.append(line).append("\n");
            }

            log.info("导出CSV成功: 行数={}", data.size());
            return csv.toString().getBytes(StandardCharsets.UTF_8);

        } catch (Exception e) {
            log.error("导出CSV失败: {}", e.getMessage());
            throw new BusinessException(ResultCode.INTERNAL_ERROR, "导出CSV失败");
        }
    }

    @Override
    public byte[] exportToPdf(String title, List<String> headers, List<Map<String, Object>> data) {
        // PDF导出功能较复杂，这里提供基础实现
        // 实际项目中可以使用iText或其他PDF库实现更复杂的布局
        log.warn("PDF导出功能暂未完全实现");
        throw new BusinessException(ResultCode.NOT_IMPLEMENTED, "PDF导出功能暂未实现");
    }

}
