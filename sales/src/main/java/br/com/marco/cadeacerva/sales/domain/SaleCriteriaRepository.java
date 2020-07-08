package br.com.marco.cadeacerva.sales.domain;

import java.util.List;

public interface SaleCriteriaRepository {
    List<Sale> findBy(SaleSearchCriteriaWrapper criteria);
}
