package com.info.org.sid.dao;

import com.info.org.sid.entities.Societe;
import com.info.org.sid.entities.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    public Flux<Transaction> findBySociete(Societe societe);
}
