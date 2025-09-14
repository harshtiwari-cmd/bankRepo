package omnicore.com.controller;

import omnicore.com.Dto.AtmRequestDto;
import omnicore.com.Dto.AtmResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAtmController {

    ResponseEntity<AtmResponseDto> registerAtm(AtmRequestDto atmRequestDto);
}
