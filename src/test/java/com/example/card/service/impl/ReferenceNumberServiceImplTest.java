package com.example.card.service.impl;

import com.example.card.services.impl.ReferenceNumberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class ReferenceNumberServiceImplTest {
    private ReferenceNumberServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ReferenceNumberServiceImpl();
    }

    @Test
    void shouldGenerateReferenceNumberWithCorrectFormat() {
        LocalDate fixedDate = LocalDate.of(2025, 9, 8);
        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(fixedDate);

            String ref = service.generateReferenceNumber("CHNL");
            assertTrue(ref.startsWith("CHNL08092025"));
            assertTrue(ref.endsWith("000001"));
        }
    }

    @Test
    void shouldIncrementSequenceForSameChannelAndDate() {
        LocalDate fixedDate = LocalDate.of(2025, 9, 8);
        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(fixedDate);

            String firstRef = service.generateReferenceNumber("CHNL");
            String secondRef = service.generateReferenceNumber("CHNL");
            assertTrue(firstRef.endsWith("000001"));
            assertTrue(secondRef.endsWith("000002"));
        }
    }

    @Test
    void shouldIsolateSequenceByChannel() {
        LocalDate fixedDate = LocalDate.of(2025, 9, 8);
        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(fixedDate);

            String ref1 = service.generateReferenceNumber("RIB");
            String ref2 = service.generateReferenceNumber("RIB");

            assertTrue(ref1.endsWith("000001"));
            assertTrue(ref2.endsWith("000002"));
            assertNotEquals(ref1, ref2);
        }
    }

    @Test
    void shouldResetSequenceForNewDate() {
        LocalDate date1 = LocalDate.of(2025, 9, 8);
        LocalDate date2 = LocalDate.of(2025, 9, 9);

        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(date1);

            String ref1 = service.generateReferenceNumber("CHNL");
            String ref2 = service.generateReferenceNumber("CHNL");

            mockDate.when(LocalDate::now).thenReturn(date2);

            String ref3 = service.generateReferenceNumber("CHNL");

            assertTrue(ref1.endsWith("000001"));
            assertTrue(ref2.endsWith("000002"));
            assertTrue(ref3.endsWith("000001"));
        }
    }

    @Test
    void shouldHandleEmptyChannelName() {
        LocalDate fixedDate = LocalDate.of(2025, 9, 8);
        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(fixedDate);

            String ref = service.generateReferenceNumber("");
            assertTrue(ref.startsWith("08092025"));
            assertTrue(ref.endsWith("000001"));
        }
    }

    @Test
    void shouldBeThreadSafeAndGenerateUniqueSequences() throws InterruptedException {
        LocalDate fixedDate = LocalDate.of(2025, 9, 8);

        try (MockedStatic<LocalDate> mockDate = Mockito.mockStatic(LocalDate.class)) {
            mockDate.when(LocalDate::now).thenReturn(fixedDate);

            int threads = 50;
            CountDownLatch latch = new CountDownLatch(threads);
            Set<String> results = new HashSet<>();
            AtomicBoolean duplicateFound = new AtomicBoolean(false);

            for (int i = 0; i < threads; i++) {
                new Thread(() -> {
                    try {
                        String ref = service.generateReferenceNumber("CHNL");
                        synchronized (results) {
                            if (!results.add(ref)) {
                                duplicateFound.set(true);
                            }
                        }
                    } finally {
                        latch.countDown();
                    }
                }).start();
            }
            latch.await();

            assertFalse(duplicateFound.get(), "Duplicate reference numbers found, thread safety issue!");
            assertEquals(threads, results.size(), "All reference numbers should be unique");
        }
    }
}

