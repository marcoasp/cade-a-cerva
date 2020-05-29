package br.com.marco.cadeacerva.sales.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaleRepository extends MongoRepository<Sale, String> {
}
