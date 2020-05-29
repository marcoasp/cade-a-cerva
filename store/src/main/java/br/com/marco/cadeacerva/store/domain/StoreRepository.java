package br.com.marco.cadeacerva.store.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author marprado - Marco Prado
 * @version 1.0 29/05/2020
 */
public interface StoreRepository extends MongoRepository<Store, String> {
}
