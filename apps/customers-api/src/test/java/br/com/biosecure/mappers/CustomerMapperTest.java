package br.com.biosecure.mappers;

import br.com.biosecure.builders.CustomerTestBuilder;
import br.com.biosecure.dto.*;
import br.com.biosecure.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerMapperTest {

    @Test
    void shouldConvertToCustomerDtoCorrectly() {
        Customer entity = CustomerTestBuilder.aCustomer().withCorporateName("GOOGLE BRASIL INTERNET LTDA").build();

        CustomerResponseDto dto = CustomerMapper.toDto(entity);

        assertEquals(entity.getCorporateName(), dto.corporateName());
        assertEquals(1, dto.taxData().size());
    }

    @Test
    void shouldConvertToSummarizedDtoCorrectly() {
        Customer entity = CustomerTestBuilder.aCustomer()
                .withEmail("test@biosecure.com")
                .withCorporateName("BIOSECURE CORPORATION")
                .withFinancialData(new FinancialData(BigDecimal.valueOf(5_000_000))) // ignored field in summary dto
                .build();

        CustomerSummaryDto dto = CustomerMapper.toSummaryDto(entity);

        assertEquals(entity.getCorporateName(), dto.corporateName());
        assertEquals(entity.getEmail(), dto.email());
    }

    @Test
    void shouldConvertToCustomerEntityCorrectly() {
        Set<AddressInputDto> addresses = Set.of(new AddressInputDto(
                "SP", "Sao Paulo",
                "Conchal",
                "Rua Duque de Caxias",
                "123A",
                "66666-666",
                true)
        );

        TaxDataInputDto td = new TaxDataInputDto(
                LocalDateTime.now(),
                LocalDate.now().minusYears(5),
                RegistrationStatus.ACTIVE,
                "random",
                new CnaeInputDto("3812-2/00", "random")
        );

        CustomerInputDto dto  = new CustomerInputDto(
                "GOOGLE BRASIL INTERNET LTDA",
                new Cnpj("06.990.590/0003-95"),
                "googlebrasil@google.com",
                addresses,
                new FinancialDataInputDto(BigDecimal.valueOf(200_000_000)),
                td
        );

        Customer customer = CustomerMapper.toEntity(dto);

        assertEquals(1, customer.getAddresses().size());
        assertEquals(1, customer.getTaxData().size());
    }

    @Test
    void shouldThrowException_WhenInputIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> CustomerMapper.toDto(null));
        NullPointerException exception2 = assertThrows(NullPointerException.class, () -> CustomerMapper.toSummaryDto(null));

        NullPointerException exception3 = assertThrows(NullPointerException.class, () -> CustomerMapper.toEntity(null));

        assertEquals("A customer entity is required", exception.getMessage());
        assertEquals("A customer entity is required", exception2.getMessage());
        assertEquals("A customer DTO is required", exception3.getMessage());
    }
}
