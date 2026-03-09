package br.com.biosecure.repository;

import br.com.biosecure.model.FinancialData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FinancialDataRepository extends
        JpaRepository<FinancialData, UUID>,
        JpaSpecificationExecutor<FinancialData> {
}
