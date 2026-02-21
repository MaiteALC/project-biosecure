package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.AddressInputDto;
import br.com.biosecure.dto.input.CustomerInputDto;
import br.com.biosecure.dto.response.*;
import br.com.biosecure.model.Address;
import br.com.biosecure.model.Customer;
import br.com.biosecure.model.TaxData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerMapper {

    public static Customer toEntity(CustomerInputDto dto) {
        if  (dto == null) {
            throw new NullPointerException("A customer DTO is required");
        }

         Customer.CustomerBuilder customerBuilder = Customer.builder()
                .corporateName(dto.corporateName())
                .email(dto.email())
                .cnpj(dto.cnpj())
                .financialData(FinancialDataMapper.toEntity(dto.financialData()))
                .taxData(TaxDataMapper.toEntity(dto.taxData()));

        Set<Address> addresses = new HashSet<>();

        for (AddressInputDto addressDto : dto.addresses()) {
            addresses.add(AddressMapper.toEntity(addressDto));
        }

        customerBuilder.addresses(addresses);

        return customerBuilder.build();
    }

    public static CustomerResponseDto toDto(Customer entity) {
        if (entity == null) {
            throw new NullPointerException("A customer entity is required");
        }

        Set<AddressResponseDto> addresses = new HashSet<>();

        for (Address address : entity.getAddresses()) {
            addresses.add(AddressMapper.toDto(address));
        }

        List<TaxDataResponseDto> taxDataDtoList = new ArrayList<>();

        for (TaxData td : entity.getTaxData()) {
            taxDataDtoList.add(TaxDataMapper.toDto(td));
        }

        FinancialDataResponseDto fd = FinancialDataMapper.toDto(entity.getFinancialData());

        return new CustomerResponseDto(entity.getCorporateName(), entity.getCnpj().getFormattedNumber(), entity.getEmail(), addresses, fd, taxDataDtoList);
    }

    public static CustomerSummaryDto toSummaryDto(Customer entity) {
        if (entity == null) {
            throw new NullPointerException("A customer entity is required");
        }

        return new CustomerSummaryDto(entity.getCorporateName(), entity.getEmail(), entity.getCnpj().getFormattedNumber(), entity.getRegistrationDate());
    }
}
