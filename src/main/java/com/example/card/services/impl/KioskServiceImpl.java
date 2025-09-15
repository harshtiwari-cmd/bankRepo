package com.example.card.services.impl;

import com.example.card.constrants.mapper.KioskMapper;
import com.example.card.constrants.model.Kiosk;
import com.example.card.dto.KioskRequestDTO;
import com.example.card.dto.KioskResponseDTO;
import com.example.card.repository.KioskRepository;
import com.example.card.services.KioskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KioskServiceImpl implements KioskService {

    @Autowired
    private  KioskRepository kioskRepository;

    @Autowired
    private KioskMapper kioskMapper;


    public KioskServiceImpl() {
    }

    @Override
    public KioskResponseDTO createKiosk(KioskRequestDTO kioskDto) {

        Kiosk kiosk = kioskMapper.toEntity(kioskDto);
        if (kiosk.getHolidayCalendar() != null) {
            kiosk.getHolidayCalendar().forEach(holiday -> holiday.setKiosk(kiosk));
        }
        Kiosk save = kioskRepository.save(kiosk);
        return kioskMapper.toDto(save);
    }


}
