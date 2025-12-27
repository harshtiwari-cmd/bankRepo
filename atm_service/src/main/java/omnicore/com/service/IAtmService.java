package omnicore.com.service;

import omnicore.com.Dto.AtmRequestDto;
import omnicore.com.Dto.AtmResponseDto;

public interface IAtmService {

    AtmResponseDto registerAtm(AtmRequestDto requestDto);

}
