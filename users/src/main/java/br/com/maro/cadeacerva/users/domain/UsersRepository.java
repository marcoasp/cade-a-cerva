package br.com.maro.cadeacerva.users.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
