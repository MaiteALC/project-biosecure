package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.CnaeInputDto;
import br.com.biosecure.dto.response.CnaeResponseDto;
import br.com.biosecure.model.Cnae;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CnaeMapperTest {

    @Test
    void shouldConvertToEntityCorrectly() {
        CnaeInputDto dto = new CnaeInputDto("8630-5/02", "test description");

        Cnae cnae = CnaeMapper.toEntity(dto);

        assertEquals(dto.code(), cnae.getFormattedCode());
        assertEquals("8630502", cnae.getUnformattedCode());
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

        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> CnaeMapper.toEntity(null));

        assertEquals("A CNAE entity is required", exception.getMessage());
        assertEquals("A CNAE DTO is required", exception2.getMessage());
    }
}
