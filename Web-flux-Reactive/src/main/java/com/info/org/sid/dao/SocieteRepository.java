package com.info.org.sid.dao;

import com.info.org.sid.entities.Societe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SocieteRepository extends ReactiveMongoRepository<Societe, String> {

}
