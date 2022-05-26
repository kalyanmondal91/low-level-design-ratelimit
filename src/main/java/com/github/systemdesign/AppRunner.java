package com.github.systemdesign;

import com.github.systemdesign.service.RateLimitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    private final RateLimitService rateLimitService;

    public AppRunner(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    public void run(String... args) throws Exception {
        rateLimitService.request("A", 1000L);
        rateLimitService.request("A", 1002L);
        rateLimitService.request("A", 1020L);
        rateLimitService.request("A", 1030L);
        rateLimitService.request("A", 1050L);
        rateLimitService.request("A", 1051L);
        rateLimitService.request("A", 1099L);
        rateLimitService.request("A", 1099L);
        rateLimitService.request("A", 1100L);
        rateLimitService.request("A", 1101L);
    }
}
