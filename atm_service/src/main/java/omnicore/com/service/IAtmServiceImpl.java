package omnicore.com.service;

import omnicore.com.Dto.AtmRequestDto;
import omnicore.com.Dto.AtmResponseDto;
import omnicore.com.Repository.AtmRepository;
import omnicore.com.entity.AtmEntity;
import org.springframework.stereotype.Service;

@Service
public class IAtmServiceImpl  implements IAtmService{

    private final AtmRepository atmRepository;

    public IAtmServiceImpl(AtmRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    @Override
    public AtmResponseDto registerAtm(AtmRequestDto requestDto) {
   AtmEntity atmEntity=     AtmEntity.builder().
                atmId(requestDto.getAtmId())
                .branchId(requestDto.getBranchId())
                .siteName(requestDto.getSiteName())
                .streetName(requestDto.getStreetName())
                .townName(requestDto.getTownName())
                .country(requestDto.getCountry())
                .postCode(requestDto.getPostCode())
                .latitude(requestDto.getLatitude())
                .longitude(requestDto.getLongitude())
                .supportedLanguages(String.join(",",requestDto.getSupportedLanguages()))
                .atmServices(requestDto.getAtmServices())
                .supportedCurrencies(String.join(",",    requestDto.getSupportedCurrencies()))
                .minimumPossibleAmount(requestDto.getMinimumPossibleAmount())
                .openTime(requestDto.getOpenTime())
                .build();
   AtmEntity saved=atmRepository.save(atmEntity);
        System.out.println("Test log from Ashish to trigger PR");


        return AtmResponseDto.builder()
           .id(saved.getId())
           .atmId(saved.getAtmId())
           .branchId(saved.getBranchId())
           .siteName(saved.getSiteName())
           .townName(saved.getTownName())
           .country(saved.getCountry())
           .openTime(saved.getOpenTime()).build();

 }




}
