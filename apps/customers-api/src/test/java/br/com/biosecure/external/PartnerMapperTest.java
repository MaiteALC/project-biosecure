package br.com.biosecure.external;

import br.com.biosecure.dto.response.PartnerResponseDto;
import br.com.biosecure.model.Partner;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PartnerMapperTest {

    @Test
    void shouldConvertToPartnerDtoCorrectly() {
        Partner partner = new Partner(2, "Test Name", "***123***", 10, "Diretor", LocalDate.now().minusYears(2), 5);

        PartnerResponseDto partnerResponseDto = PartnerMapper.toDto(partner);

        assertEquals(partner.name(), partnerResponseDto.name());
        assertEquals(partner.type(), partnerResponseDto.type());
    }

    @Test
    void shouldConvertToPartnerEntityCorrectly() {
        PartnerExternalDto dto = new PartnerExternalDto(2, "Test Name", "***456***", 9, "Socio", LocalDate.now().minusYears(2), 4);

        Partner partner = PartnerMapper.toEntity(dto);

        assertEquals(partner.name(), dto.name());
        assertEquals(partner.type(), dto.type());
    }

    @Test
    void shouldThrowException_WhenInputIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> PartnerMapper.toDto(null));

        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> PartnerMapper.toEntity(null));

        assertEquals("A partner entity is required", exception.getMessage());
        assertEquals("A partner DTO is required", exception2.getMessage());
    }
}
