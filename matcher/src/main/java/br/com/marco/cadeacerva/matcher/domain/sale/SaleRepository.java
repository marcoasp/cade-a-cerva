package br.com.marco.cadeacerva.matcher.domain.sale;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<Sale, String>, SaleCriteriaRepository {
}
