package omnicore.com;

import omnicore.com.Dto.AtmRequestDto;
import omnicore.com.Repository.AtmRepository;
import omnicore.com.entity.AtmEntity;
import omnicore.com.service.IAtmServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import static org.mockito.ArgumentMatchers.any;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AtmServiceTest {
    @Mock
    private AtmRepository atmRepository;

    @InjectMocks
    private IAtmServiceImpl atmService;

    public AtmServiceTest()
    {
        MockitoAnnotations.openMocks(this);

    }
    @Test
    void testRegisterAtm()
    {
        AtmRequestDto dto= AtmRequestDto.builder()
                .atmId("AB123456")
                .branchId("402519")
                .siteName("central mall")
               .supportedLanguages(List.of("en", "hi"))
                .supportedCurrencies(List.of("INR"))
                .townName("Hyderabad")
                .country("IN")
                .openTime("08:00")
                .build();

        AtmEntity saved=AtmEntity.builder()
                .id(3L)
                .atmId("AB123456")
                .branchId("402519")
                .siteName("central mall")
                .townName("Hyderabad")
                .country("IN")
                .openTime("08:00")
                .build();
        when(atmRepository.save(any())).thenReturn(saved);
        assertEquals("AB123456",atmService.registerAtm(dto).getAtmId());
    }
}
