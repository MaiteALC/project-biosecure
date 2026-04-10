package br.com.biosecure.specifications;

import br.com.biosecure.model.Customer;
import br.com.biosecure.queryfilters.CustomerQueryFilter;
import br.com.biosecure.queryfilters.IncludeParam;
import br.com.biosecure.queryfilters.TaxDataQueryFilter;
import br.com.biosecure.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@DataJpaTest
@Sql("/seed-data.sql")
class TaxDataSpecsTest {

    private static final int TOTAL_TEST_DB_REGISTERS = 5;

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldReturnAllCorrectly_WhenEmptySpecsAreProvided() {
        TaxDataQueryFilter tdqf = new TaxDataQueryFilter(null, null, null, null, null, null, null, null, null, null);

        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, null, null, tdqf, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.TAX_DATA), cqf);

        assertEquals(TOTAL_TEST_DB_REGISTERS, repository.findAll(specs).size());
    }

    @Test
    void shouldReturnAllCorrectly_WhenNullSpecsAreProvided() {
        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, null, null, null, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.TAX_DATA), cqf);

        assertEquals(TOTAL_TEST_DB_REGISTERS, repository.findAll(specs).size());
    }

    @Test
    void shouldReturnCorrectly_WhenOneSpecIsProvided() {
        TaxDataQueryFilter tdqf = new TaxDataQueryFilter(null, null, null, null, null, LocalDate.of(2020, 1, 1), null, null, null, null);

        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, null, null, tdqf, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.TAX_DATA), cqf);

        assertEquals(4, repository.findAll(specs).size());
    }

    @Test
    void shouldReturnCorrectly_WhenMultipleSpecsAreProvided() {
        TaxDataQueryFilter tdqf = new TaxDataQueryFilter(LocalDate.now(), null, null, null, LocalDate.of(1990, 1, 1), LocalDate.of(2020, 1, 1), null, null, null, null);

        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, null, null, tdqf, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.TAX_DATA), cqf);

        assertEquals(2, repository.findAll(specs).size());
    }
}
