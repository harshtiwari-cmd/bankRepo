package omnicore.com.Dto;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtmRequestDto {
    private String atmId;
    private String branchId;
    private String siteName;
    private String streetName;
    private String townName;
    private String country;
    private String postCode;
    private Double latitude;
    private Double longitude;

    @ElementCollection
    private List<String> supportedLanguages;

    @ElementCollection
    private List<String> atmServices;

    @ElementCollection
    private List<String> supportedCurrencies;

    private Integer minimumPossibleAmount;
    private String openTime;
}
