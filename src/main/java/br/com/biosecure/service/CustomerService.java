package br.com.biosecure.service;

import br.com.biosecure.dto.input.CustomerInputDto;
import br.com.biosecure.dto.response.CustomerDto;
import br.com.biosecure.mappers.CustomerMapper;
import br.com.biosecure.model.Customer;
import br.com.biosecure.queryfilters.CustomerQueryFilter;
import br.com.biosecure.queryfilters.IncludeParam;
import br.com.biosecure.repository.CustomerRepository;
import br.com.biosecure.specifications.CustomerSpecs;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private Set<IncludeParam> resolveIncludeParam(Set<IncludeParam> rawParams) {
        Set<IncludeParam> includeParams = new HashSet<>();

        if (rawParams == null || rawParams.isEmpty()) {
            return includeParams;
        }

        if (rawParams.contains(IncludeParam.FULL) && rawParams.contains(IncludeParam.SUMMARIZED)) {
            return  includeParams;
        }

        if (rawParams.contains(IncludeParam.FULL)) {
            includeParams.add(IncludeParam.ADDRESS);
            includeParams.add(IncludeParam.FINANCIAL_DATA);
            includeParams.add(IncludeParam.TAX_DATA);

            return includeParams;
        }

        if (rawParams.contains(IncludeParam.SUMMARIZED)) {
            includeParams.add(IncludeParam.SUMMARIZED);

            return includeParams;
        }

        return rawParams;
    }

    public List<CustomerDto> searchBySpecification(CustomerQueryFilter queryFilter, Set<IncludeParam> includeParam) {
        Set<IncludeParam> resolvedIncludeParam = resolveIncludeParam(includeParam);

        Specification<Customer> spec = CustomerSpecs.buildSpecification(resolvedIncludeParam, queryFilter);

        List<Customer> customers = customerRepository.findAll(spec);

        List<CustomerDto> customerDtos = new ArrayList<>();

        if (!customers.isEmpty()) {
            if (resolvedIncludeParam.contains(IncludeParam.SUMMARIZED)) {
                for (Customer customer : customers) {
                    customerDtos.add(CustomerMapper.toSummaryDto(customer));
                }

                return customerDtos;
            }

            for (Customer customer : customers) {
                customerDtos.add(CustomerMapper.toDto(customer, resolvedIncludeParam));
            }
        }

        return customerDtos;
    }

    public Optional<CustomerDto> searchById(UUID id, Set<IncludeParam> includeParam) {
        Set<IncludeParam> resolvedIncludeParam = resolveIncludeParam(includeParam);

        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer != null) {
            if (resolvedIncludeParam.contains(IncludeParam.SUMMARIZED)) {
                return Optional.of(CustomerMapper.toSummaryDto(customer));
            }
            return Optional.of(CustomerMapper.toDto(customer, resolvedIncludeParam));
        }

        return Optional.empty();
    }

    public List<CustomerDto> searchByIds(Set<UUID> customerIds, Set<IncludeParam> includeParam) {
        Set<IncludeParam> resolvedIncludeParam = resolveIncludeParam(includeParam);

        List<Customer> customers = customerRepository.findAllById(customerIds);
        List<CustomerDto> customerDtos = new ArrayList<>();

        if (!customers.isEmpty()) {
            if (resolvedIncludeParam.contains(IncludeParam.SUMMARIZED)) {
                for (Customer customer : customers) {
                    customerDtos.add(CustomerMapper.toSummaryDto(customer));
                }

                return customerDtos;
            }

            for (Customer customer : customers) {
                customerDtos.add(CustomerMapper.toDto(customer, resolvedIncludeParam));
            }
        }

        return customerDtos;
    }

    @Transactional
    public void deleteById(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
    }

    @Transactional
    public CustomerDto registerCustomer(CustomerInputDto inputDto) {
        Customer entity = CustomerMapper.toEntity(inputDto);

        customerRepository.save(entity);

        return CustomerMapper.toDto(entity, Set.of(IncludeParam.FULL));
    }

    @Transactional
    public List<CustomerDto> registerCustomers(List<CustomerInputDto> inputDtos) {
        List<Customer> entities = new ArrayList<>();
        List<CustomerDto> responseDtos = new ArrayList<>();

        for (CustomerInputDto dto : inputDtos) {
            Customer entity = CustomerMapper.toEntity(dto);

            entities.add(entity);
            responseDtos.add(CustomerMapper.toSummaryDto(entity));
        }

        customerRepository.saveAll(entities);

        return responseDtos;
    }

    // TODO add methods for update data
}
