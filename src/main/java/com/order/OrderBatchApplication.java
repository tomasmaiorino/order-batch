package com.order;

import com.order.service.ProcessCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

import static java.lang.System.exit;

@SpringBootApplication
public class OrderBatchApplication {

    @Autowired
    private ProcessCommissionService processCommissionService;

    public static void main(String[] args) {
        SpringApplication.run(OrderBatchApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ApplicationContext ctx) {
        return args -> {

            if (Objects.isNull(args) || args.length == 0) {
                throw new IllegalArgumentException("The file path is required as param.");
            }
            processCommissionService.processCommission(args[0]);
            exit(0);
        };
    }
}
