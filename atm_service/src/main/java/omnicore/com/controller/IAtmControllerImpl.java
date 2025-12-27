package omnicore.com.controller;

import omnicore.com.Dto.AtmRequestDto;
import omnicore.com.Dto.AtmResponseDto;
import omnicore.com.service.IAtmService;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/atms")
public class IAtmControllerImpl implements  IAtmController{

    private final IAtmService iAtmService;

    public IAtmControllerImpl(IAtmService iAtmService) {
        this.iAtmService = iAtmService;
    }

    @PostMapping
    public ResponseEntity<AtmResponseDto>registerAtm(@RequestBody AtmRequestDto atmRequestDto)  throws ConfigDataResourceNotFoundException
    {

        return  ResponseEntity.ok(iAtmService.registerAtm(atmRequestDto));
    }
}
