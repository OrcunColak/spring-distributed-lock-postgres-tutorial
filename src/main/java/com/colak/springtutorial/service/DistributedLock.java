package com.colak.springtutorial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DistributedLock {

    private final JdbcTemplate jdbcTemplate;

    public boolean acquire(String lockName) {
        long lockId = lockId(lockName);
        String sql = "SELECT pg_try_advisory_lock(?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, lockId));
    }

    public boolean release(String lockName) {
        long lockId = lockId(lockName);
        String sql = "SELECT pg_advisory_unlock(?)";
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, lockId));
    }

    // Calculate a positive lock ID from the lock name
    private long lockId(String lockName) {
        // Get the hash code of the lock name
        int hashCode = lockName.hashCode();
        // Ensure the ID is within the positive int range
        return Math.abs(hashCode) % Integer.MAX_VALUE;
    }
}
