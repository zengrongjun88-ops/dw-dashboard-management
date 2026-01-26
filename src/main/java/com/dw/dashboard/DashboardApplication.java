package com.dw.dashboard;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 数据仓库报表管理系统启动类
 *
 * @author DW Team
 * @since 2026-01-26
 */
@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@MapperScan("com.dw.dashboard.mapper")
public class DashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("数据仓库报表管理系统启动成功！");
        System.out.println("API文档地址: http://localhost:8080/api/doc.html");
        System.out.println("========================================\n");
    }

}
