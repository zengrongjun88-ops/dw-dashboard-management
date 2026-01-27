package com.dw.dashboard.service;

import java.util.List;
import java.util.Map;

/**
 * 导出服务接口
 *
 * @author DW Team
 * @since 2026-01-27
 */
public interface IExportService {

    /**
     * 导出为Excel
     *
     * @param headers 表头
     * @param data 数据
     * @return Excel文件字节数组
     */
    byte[] exportToExcel(List<String> headers, List<Map<String, Object>> data);

    /**
     * 导出为CSV
     *
     * @param headers 表头
     * @param data 数据
     * @return CSV文件字节数组
     */
    byte[] exportToCsv(List<String> headers, List<Map<String, Object>> data);

    /**
     * 导出为PDF
     *
     * @param title 标题
     * @param headers 表头
     * @param data 数据
     * @return PDF文件字节数组
     */
    byte[] exportToPdf(String title, List<String> headers, List<Map<String, Object>> data);

}
