package com.example.pool;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class PoolDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PoolDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner runPoolCheck(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        return args -> {
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS notes (id INT PRIMARY KEY, message VARCHAR(255))");
            jdbcTemplate.update("MERGE INTO notes (id, message) KEY(id) VALUES (1, 'Hello HikariCP')");

            String message = jdbcTemplate.queryForObject(
                "SELECT message FROM notes WHERE id = 1",
                String.class
            );

            System.out.println("Query result: " + message);

            if (dataSource instanceof HikariDataSource hikari) {
                System.out.println("Active pool name: " + hikari.getPoolName());
                System.out.println("Max pool size: " + hikari.getMaximumPoolSize());
            }
        };
    }
}
