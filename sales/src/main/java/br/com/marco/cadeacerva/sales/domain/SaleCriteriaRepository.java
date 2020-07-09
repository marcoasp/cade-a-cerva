package br.com.marco.cadeacerva.sales.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleCriteriaRepository {
    Page<Sale> findBy(SaleSearchCriteriaWrapper criteria, Pageable pageable);
}
