package br.com.biosecure.mappers;

import br.com.biosecure.dto.input.AddressInputDto;
import br.com.biosecure.dto.input.CustomerInputDto;
import br.com.biosecure.dto.response.*;
import br.com.biosecure.model.Address;
import br.com.biosecure.model.Customer;
import br.com.biosecure.model.TaxData;
import br.com.biosecure.queryfilters.IncludeParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A utility class providing static methods to map data between the {@link Customer}
 * aggregate root and its respective Data Transfer Objects (DTOs).
 * <p>
 * <strong>Design Principle:</strong> This mapping process acts as an anti-corruption layer
 * between the external API contracts and the internal domain model. By isolating the
 * {@code Customer} entity from web-layer concerns, we prevent over-posting vulnerabilities
 * and allow the API payload structures to evolve independently of the core business rules.
 * <p>
 * To handle the complexity of nested objects, this class orchestrates the conversion by
 * delegating specific mappings to specialized classes, such as {@link AddressMapper}.
 *
 * @see Customer
 * @see CustomerInputDto
 * @see CustomerFullResponseDto
 * @see CustomerSummaryResponseDto
 * @see FinancialDataMapper
 * @see TaxDataMapper
 * @see AddressMapper
 *
 * @since 1.0.0
 * @author MaiteALC
 */
public class CustomerMapper {

    /**
     * Maps a {@link CustomerInputDto} into a valid {@link Customer} domain entity.
     * <p>
     * This method constructs the aggregate root by extracting primitive data and delegating
     * the conversion of nested objects to their respective specialized mappers
     * (e.g., {@link AddressMapper}, {@link FinancialDataMapper}).
     *
     * @param dto the input data transfer object to be mapped
     * @return the fully instantiated {@code Customer} entity
     * @throws NullPointerException if the provided {@code dto} is null
     * @throws br.com.biosecure.model.InvalidCustomerAttributeException if any field
     * from the DTO fails domain validation during the {@code Customer} instantiation
     */
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

    /**
     * Maps a {@link Customer} entity into a {@link CustomerFullResponseDto}, dynamically
     * attaching related data based on the requested inclusion parameters.
     * <p>
     * The mapping of nested objects (such as Addresses or Tax Data) is conditionally
     * executed only if explicitly requested within the {@code includeParams} set.
     * This strategy ensures optimized API responses and avoids unnecessary object mapping.
     *
     * @param entity the domain entity to be mapped
     * @param includeParams a {@link Set} of {@link IncludeParam} determining which
     * nested resources should be eagerly mapped and included
     *
     * @return the resulting DTO containing the requested data payload
     *
     * @throws NullPointerException if either the {@code entity} or {@code includeParams} is null
     */
    public static CustomerFullResponseDto toDto(Customer entity, Set<IncludeParam> includeParams) {
        if (entity == null) {
            throw new NullPointerException("A customer entity is required");
        }

        if (includeParams == null) {
            throw new NullPointerException("A customer include param is required");
        }

        Set<AddressResponseDto> addresses = null;
        List<TaxDataResponseDto> taxDataDtoList = null;

        if (includeParams.contains(IncludeParam.ADDRESS) || includeParams.contains(IncludeParam.FULL)) {
            addresses  = new HashSet<>();

            for (Address address : entity.getAddresses()) {
                addresses.add(AddressMapper.toDto(address));
            }
        }

        if (includeParams.contains(IncludeParam.TAX_DATA) || includeParams.contains(IncludeParam.FULL)) {
            taxDataDtoList = new ArrayList<>();

            for (TaxData td : entity.getTaxData()) {
                taxDataDtoList.add(TaxDataMapper.toDto(td));
            }
        }

        FinancialDataResponseDto fd = null;

        if (includeParams.contains(IncludeParam.FINANCIAL_DATA) || includeParams.contains(IncludeParam.FULL)) {
            fd = FinancialDataMapper.toDto(entity.getFinancialData());
        }

        return new CustomerFullResponseDto(entity.getId(), entity.getRegistrationDate(), entity.getCorporateName(), entity.getCnpj().getFormattedNumber(), entity.getEmail(), addresses, fd, taxDataDtoList);
    }

    /**
     * Converts a {@link Customer} entity into a lightweight {@link CustomerSummaryResponseDto}.
     * <p>
     * This method is intended for use cases that require reduced payloads, such as
     * list views or search results, returning only essential identification and
     * registration data without processing heavy nested objects.
     *
     * @param entity the domain entity to be summarized
     * @return a summarized version of the customer DTO
     * @throws NullPointerException if the provided {@code entity} is null
     */
    public static CustomerSummaryResponseDto toSummaryDto(Customer entity) {
        if (entity == null) {
            throw new NullPointerException("A customer entity is required");
        }

        return new CustomerSummaryResponseDto(entity.getCorporateName(), entity.getEmail(), entity.getCnpj().getFormattedNumber(), entity.getRegistrationDate());
    }
}
