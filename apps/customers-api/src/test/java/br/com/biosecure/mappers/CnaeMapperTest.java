package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.CnaeInputDto;
import br.com.biosecure.dto.response.CnaeResponseDto;
import br.com.biosecure.external.CnaeExternalDto;
import br.com.biosecure.model.Cnae;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CnaeMapperTest {

    @Test
    void shouldConvertToEntityCorrectly_WhenInputIsInputDto() {
        CnaeInputDto dto = new CnaeInputDto("8630-5/02", "test description");

        Cnae cnae = CnaeMapper.toEntity(dto);

        assertEquals(dto.code(), cnae.getFormattedCode());
        assertEquals("8630502", cnae.getUnformattedCode());
    }

    @Test
    void shouldConvertToDtoCorrectly_WhenInputIsExternalDto() {
        CnaeExternalDto dto = new CnaeExternalDto("7120100", "test description");

        Cnae cnae = CnaeMapper.toEntity(dto);

        assertEquals(dto.code(), cnae.getUnformattedCode());
        assertEquals("7120-1/00", cnae.getFormattedCode());
    }

    @Test
    void shouldConvertToDtoCorrectly() {
        Cnae cnae = new Cnae("8630-5/02",  "test description");

        CnaeResponseDto dto = CnaeMapper.toDto(cnae);

        assertEquals(cnae.getFormattedCode(), dto.code());
    }

    @Test
    void shouldThrowException_WhenInputIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> CnaeMapper.toDto(null));

        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> CnaeMapper.toEntity((CnaeInputDto) null));

        NullPointerException exception3 = assertThrows(NullPointerException.class, () -> CnaeMapper.toEntity((CnaeExternalDto) null));

        assertEquals("A CNAE entity is required", exception.getMessage());
        assertEquals("A CNAE input DTO is required", exception2.getMessage());
        assertEquals("A CNAE external DTO is required", exception3.getMessage());
    }
}
