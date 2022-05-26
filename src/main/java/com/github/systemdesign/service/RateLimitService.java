package com.github.systemdesign.service;

public interface RateLimitService {
    void request(String clientId, Long timestamp);
}
