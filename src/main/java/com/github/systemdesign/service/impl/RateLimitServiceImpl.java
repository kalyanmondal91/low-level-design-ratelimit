package com.github.systemdesign.service.impl;

import com.github.systemdesign.service.RateLimitService;
import com.github.systemdesign.strategy.BucketStrategy;
import com.github.systemdesign.strategy.LimitStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {
    private final LimitStrategy limitStrategy;

    public RateLimitServiceImpl(BucketStrategy bucketStrategy) {
        this.limitStrategy = bucketStrategy;
    }

    @Override
    public void request(String clientId, Long timestamp) {
        if(isAllowed(clientId, timestamp)) {
            log.info("Request Served - Client - {}, Hit @ {}", clientId, timestamp);
        } else {
            log.info("Request Blocked - Client - {}, Miss @ {}", clientId, timestamp);
        }
    }

    private boolean isAllowed(String clientId, Long timestamp) {
        return limitStrategy.validateRequest(clientId, timestamp);
    }
}
