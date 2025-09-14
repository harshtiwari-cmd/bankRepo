package omnicore.com;
import com.fasterxml.jackson.databind.ObjectMapper;
import omnicore.com.Dto.AtmRequestDto;
import omnicore.com.controller.IAtmController;
import omnicore.com.controller.IAtmControllerImpl;
import omnicore.com.service.IAtmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class IAtmControllerImplTest {


    private MockMvc mockMvc;

    @Mock
    private IAtmService iAtmService;

    @InjectMocks
    private IAtmControllerImpl iAtmController;

    private final ObjectMapper objectMapper=new ObjectMapper();

    @Test
    void testRegisterAtm () throws Exception{
        mockMvc= MockMvcBuilders.standaloneSetup(iAtmController).build();

        AtmRequestDto dto= AtmRequestDto.builder()
                .atmId("AB123456")
                .branchId("402519")
                .siteName("central mall")
                .townName("Hyderabad")
                .country("IN")
                .openTime("08:00")
                .build();

        when(iAtmService.registerAtm(dto)).thenReturn(null);

        mockMvc.perform(post("/api/atms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }



}
