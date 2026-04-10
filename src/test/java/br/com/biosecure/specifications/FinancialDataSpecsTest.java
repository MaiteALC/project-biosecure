package br.com.biosecure.specifications;

import br.com.biosecure.model.FinancialData;
import br.com.biosecure.queryfilters.FinancialDataQueryFilter;
import br.com.biosecure.repository.FinancialDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles(profiles = "test")
@DataJpaTest
@Sql("/seed-data.sql")
class FinancialDataSpecsTest {

    private static final int TOTAL_TEST_DB_REGISTERS = 5;

    @Autowired
    private FinancialDataRepository repository;

    @Test
    void shouldReturnAllCorrectly_WhenEmptySpecsAreProvided() {

        Specification<FinancialData> emptySpecs = FinancialDataSpecs.buildSpecification(
                new FinancialDataQueryFilter(null, null, null, null, null, null, null, null, null, null)
        );

        assertEquals(TOTAL_TEST_DB_REGISTERS, repository.findAll(emptySpecs).size());
    }

    @Test
    void shouldReturnAllCorrectly_WhenNullSpecsAreProvided() {
        Specification<FinancialData> nullSpecs = null;

        assertEquals(TOTAL_TEST_DB_REGISTERS, repository.findAll(nullSpecs).size());
    }

    @Test
    void shouldReturnCorrectly_WhenOneSpecIsProvided() {
        Specification<FinancialData> specs = FinancialDataSpecs.buildSpecification(
                new FinancialDataQueryFilter(null, null, null, BigDecimal.valueOf(600000), null, null, null, null, null, null)
        );

        assertEquals(2, repository.findAll(specs).size());
    }

    @Test
    void shouldReturnCorrectly_WhenMultipleSpecsAreProvided() {
        Specification<FinancialData> specs = FinancialDataSpecs.buildSpecification(
                new FinancialDataQueryFilter(null, null, BigDecimal.valueOf(400000), BigDecimal.valueOf(600000), null, null, null, null, null, null)
        );

        assertEquals(1, repository.findAll(specs).size());
    }
}
