package com.example.card.services.impl;

import com.example.card.services.ReferenceNumberService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReferenceNumberServiceImpl implements ReferenceNumberService {
    private final Map<String, AtomicInteger> sequenceMap = new ConcurrentHashMap<>();
    @Override
    public synchronized String generateReferenceNumber(String channelName) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String key = channelName + date;
        AtomicInteger seq = sequenceMap.computeIfAbsent(key, k -> new AtomicInteger(0));
        int sequence = seq.incrementAndGet();
        String formattedSequence = String.format("%06d", sequence);
        return channelName + date + formattedSequence;
    }
}
