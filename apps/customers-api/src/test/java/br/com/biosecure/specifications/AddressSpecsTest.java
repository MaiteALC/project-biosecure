package br.com.biosecure.specifications;

import br.com.biosecure.model.Customer;
import br.com.biosecure.queryfilters.AddressQueryFilter;
import br.com.biosecure.queryfilters.CustomerQueryFilter;
import br.com.biosecure.queryfilters.IncludeParam;
import br.com.biosecure.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@DataJpaTest
@Sql("/seed-data.sql")
class AddressSpecsTest {

    private static final int TOTAL_TEST_DB_REGISTERS = 5;

    @Autowired
    private CustomerRepository repository;

    @Test
    void shouldReturnAllCorrectly_WhenEmptySpecsAreProvided() {
        AddressQueryFilter aqf = new AddressQueryFilter(null, null, null, null, null, null, null);

        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, aqf, null, null, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.ADDRESS), cqf);

        assertEquals(TOTAL_TEST_DB_REGISTERS, repository.findAll(specs).size());
    }

    @Test
    void shouldReturnAllCorrectly_WhenNullSpecsAreProvided() {
        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, null, null, null, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.ADDRESS), cqf);

        assertEquals(TOTAL_TEST_DB_REGISTERS, repository.findAll(specs).size());
    }

    @Test
    void shouldReturnCorrectly_WhenOneSpecIsProvided() {
        AddressQueryFilter aqf = new AddressQueryFilter("PE", null, null, null, null, null, null);

        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, aqf, null, null, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.ADDRESS), cqf);

        assertEquals(1, repository.findAll(specs).size());
    }

    @Test
    void shouldReturnCorrectly_WhenMultipleSpecsAreProvided() {
        AddressQueryFilter aqf = new AddressQueryFilter("SP", "são paulo", null, null, null, "01311-000", null);

        CustomerQueryFilter cqf = new CustomerQueryFilter(null, null, null, null, aqf, null, null, null, null, null);

        Specification<Customer> specs = CustomerSpecs.buildSpecification(Set.of(IncludeParam.ADDRESS), cqf);

        assertEquals(1, repository.findAll(specs).size());
    }
}
