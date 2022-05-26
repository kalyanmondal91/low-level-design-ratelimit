package com.github.systemdesign.strategy;

public interface LimitStrategy {
    boolean validateRequest(String clientId, Long timestamp);
}
