package com.example.pool;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Map;

@RestController
public class PoolStatusController {

    private final DataSource dataSource;

    public PoolStatusController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GetMapping("/pool-status")
    public Map<String, Object> status() {
        if (dataSource instanceof HikariDataSource hikari) {
            HikariPoolMXBean pool = hikari.getHikariPoolMXBean();
            return Map.of(
                "poolName", hikari.getPoolName(),
                "activeConnections", pool != null ? pool.getActiveConnections() : -1,
                "idleConnections", pool != null ? pool.getIdleConnections() : -1,
                "totalConnections", pool != null ? pool.getTotalConnections() : -1,
                "threadsAwaitingConnection", pool != null ? pool.getThreadsAwaitingConnection() : -1
            );
        }
        return Map.of("status", "DataSource is not HikariDataSource");
    }
}
