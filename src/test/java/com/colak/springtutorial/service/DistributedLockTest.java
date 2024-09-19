package com.colak.springtutorial.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DistributedLockTest {

    @Autowired
    private DistributedLock distributedLock;

    @Test
    void acquire() {
        String lockName = "my-lock";
        boolean acquire = distributedLock.acquire(lockName);
        assertTrue(acquire);

        boolean release = distributedLock.release(lockName);
        assertTrue(release);
    }
}