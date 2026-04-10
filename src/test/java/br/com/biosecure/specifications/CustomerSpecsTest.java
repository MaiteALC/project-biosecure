package br.com.biosecure.specifications;

import br.com.biosecure.model.Customer;
import br.com.biosecure.queryfilters.*;
import br.com.biosecure.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@DataJpaTest
@Sql("/seed-data.sql")
class CustomerSpecsTest {

    private static final int TOTAL_TEST_DB_REGISTERS = 5;

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldReturnAllCorrectly_WhenEmptySpecsAreProvided() {

        Specification<Customer> emptySpecs = CustomerSpecs.buildSpecification(
                Set.of(IncludeParam.SUMMARIZED),
                new CustomerQueryFilter(null, null, null, null, null, null, null, null, null, null)
        );

        List<Customer> customers = repository.findAll(emptySpecs);

        assertEquals(TOTAL_TEST_DB_REGISTERS, customers.size());
    }

    @Test
    void shouldReturnAllCorrectly_WhenNullSpecsAreProvided() {
        Specification<Customer> nullSpecs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.SUMMARIZED), null);

        List<Customer> customers = repository.findAll(nullSpecs);

        assertEquals(TOTAL_TEST_DB_REGISTERS, customers.size());
    }

    @Test
    void shouldReturnCorrectly_WhenOneSpecIsProvided() {
        Specification<Customer> specs = CustomerSpecs.buildSpecification(
                Set.of(IncludeParam.ADDRESS),
                new CustomerQueryFilter("ltda", null, null, null, null, null, null, null, null, null)
        );

        List<Customer> customers = repository.findAll(specs);

        assertEquals(4, customers.size());
    }

    @Test
    void shouldReturnCorrectly_WhenMultipleSpecsAreProvided() {
        FinancialDataQueryFilter fd = new FinancialDataQueryFilter(null, null, null, BigDecimal.valueOf(500000), null, null, null, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(
                new HashSet<>(),
                new CustomerQueryFilter(null, null, null, null, null, fd, null, null, LocalDate.of(2000, 1, 1), null)
        );

        List<Customer> customers = repository.findAll(specs);

        assertEquals(2, customers.size());
    }

    @Test
    void shouldReturnCorrectly_WhenJoinsAreNecessary() {
        AddressQueryFilter aqf = new AddressQueryFilter("SP", null, null, null, null, null, null);

        TaxDataQueryFilter tdqf = new TaxDataQueryFilter(null, null, null, null, null, LocalDate.of(2020, 1, 1), null, null, null, null);

        FinancialDataQueryFilter fdqf = new FinancialDataQueryFilter(null, null, BigDecimal.valueOf(501000), null, null, null, null, null, null, null);

        CustomerQueryFilter cqf = new CustomerQueryFilter("s.a", null, null, null, aqf, fdqf, tdqf, null, null, null);

        List<Customer> customers = repository.findAll(CustomerSpecs.buildSpecification(new HashSet<>(),cqf));

        assertEquals(1, customers.size());
    }
}
