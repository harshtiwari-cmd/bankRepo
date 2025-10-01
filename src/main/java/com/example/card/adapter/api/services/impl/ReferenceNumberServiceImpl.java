package com.example.card.adapter.api.services.impl;

import com.example.card.adapter.api.services.ReferenceNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ReferenceNumberServiceImpl implements ReferenceNumberService {
    private final Map<String, AtomicInteger> sequenceMap = new ConcurrentHashMap<>();

    @Override
    public synchronized String generateReferenceNumber(String channelName) {

        log.info("Generating reference number for channel: {}", channelName);
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
        String key = channelName + date;
        AtomicInteger seq = sequenceMap.computeIfAbsent(key, k -> new AtomicInteger(0));
        int sequence = seq.incrementAndGet();
        String formattedSequence = String.format("%06d", sequence);
        String refNum = channelName + date + formattedSequence;
        log.debug("Generate reference number [{}] for channel [{}] with sequence [{}] on date [{}]", refNum, channelName, sequence, date);
        return refNum;
    }
}
