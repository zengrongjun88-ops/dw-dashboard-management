package com.dw.dashboard.util;

import com.dw.dashboard.common.ResultCode;
import com.dw.dashboard.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * SQL安全验证工具类
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Slf4j
@Component
public class SqlSecurityUtil {

    /**
     * 危险SQL关键字列表
     */
    private static final List<String> DANGEROUS_KEYWORDS = Arrays.asList(
            "DROP", "DELETE", "TRUNCATE", "INSERT", "UPDATE",
            "CREATE", "ALTER", "GRANT", "REVOKE",
            "EXEC", "EXECUTE", "CALL",
            "DECLARE", "CURSOR",
            "SHUTDOWN", "KILL"
    );

    /**
     * SQL注释模式
     */
    private static final List<Pattern> COMMENT_PATTERNS = Arrays.asList(
            Pattern.compile("--.*", Pattern.CASE_INSENSITIVE),
            Pattern.compile("/\\*.*?\\*/", Pattern.CASE_INSENSITIVE | Pattern.DOTALL),
            Pattern.compile("#.*", Pattern.CASE_INSENSITIVE)
    );

    /**
     * 多语句模式（检测分号）
     */
    private static final Pattern MULTI_STATEMENT_PATTERN = Pattern.compile(";\\s*\\w+", Pattern.CASE_INSENSITIVE);

    /**
     * 验证SQL安全性
     *
     * @param sql SQL语句
     * @return true-安全，false-不安全
     */
    public boolean validateSql(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new BusinessException(ResultCode.SQL_SECURITY_ERROR, "SQL语句不能为空");
        }

        String upperSql = sql.toUpperCase();

        // 1. 检查危险关键字
        for (String keyword : DANGEROUS_KEYWORDS) {
            if (upperSql.contains(keyword)) {
                log.warn("SQL包含危险关键字: {}", keyword);
                throw new BusinessException(ResultCode.SQL_SECURITY_ERROR,
                        "SQL包含危险关键字: " + keyword);
            }
        }

        // 2. 检查SQL注释
        for (Pattern pattern : COMMENT_PATTERNS) {
            if (pattern.matcher(sql).find()) {
                log.warn("SQL包含注释");
                throw new BusinessException(ResultCode.SQL_SECURITY_ERROR, "SQL不允许包含注释");
            }
        }

        // 3. 检查多语句执行
        if (MULTI_STATEMENT_PATTERN.matcher(sql).find()) {
            log.warn("SQL包含多条语句");
            throw new BusinessException(ResultCode.SQL_SECURITY_ERROR, "不允许执行多条SQL语句");
        }

        // 4. 必须是SELECT语句
        if (!upperSql.trim().startsWith("SELECT")) {
            log.warn("SQL不是SELECT语句");
            throw new BusinessException(ResultCode.SQL_SECURITY_ERROR, "只允许执行SELECT查询语句");
        }

        return true;
    }

    /**
     * 清理SQL（移除多余空格和换行）
     *
     * @param sql SQL语句
     * @return 清理后的SQL
     */
    public String cleanSql(String sql) {
        if (sql == null) {
            return null;
        }
        return sql.replaceAll("\\s+", " ").trim();
    }

}
