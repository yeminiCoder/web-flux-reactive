package com.info.org.sid.web;


import com.info.org.sid.dao.SocieteRepository;
import com.info.org.sid.dao.TransactionRepository;
import com.info.org.sid.entities.Societe;
import com.info.org.sid.entities.Transaction;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@RestController
public class TransactionRestFullReactiveController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    SocieteRepository societeRepository;

    @GetMapping(value = "/transactions")
    public Flux<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @GetMapping(value = "/transactions/{id}")
    public Mono<Transaction> findOne(@PathVariable String id) {
        return transactionRepository.findById(id);
    }

    @PostMapping("/transactions")
    public Mono<Transaction> save(@RequestBody Transaction t) {
        return transactionRepository.save(t);
    }

    @DeleteMapping(value = "/transactions/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return transactionRepository.deleteById(id);
    }


    @PutMapping(value = "/transactions/{id}")
    public Mono<Transaction> update(@RequestBody Transaction t, @PathVariable String id) {
        t.setId(id);
        return transactionRepository.save(t);
    }


    @GetMapping(value = "/streamTransactions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> streamTransactions() {
        return transactionRepository.findAll();
    }


    @GetMapping(value = "/transactionsByCompany/{id}")
    public Flux<Transaction> streamTransaction(@PathVariable String id) {
        Societe s = new Societe();
        s.setId(id);
        return transactionRepository.findBySociete(s);
    }

    @GetMapping(value = "/streamTransactionsByCompany/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Transaction> stream(@PathVariable String id) {
        return societeRepository.findById(id)
                .flatMapMany(soc -> {
                    Flux<Long> interval = Flux.interval(Duration.ofMillis(1000));
                    Flux<Transaction> transactionFlux = Flux.fromStream(Stream.generate(() -> {
                        Transaction transaction = new Transaction();
                        transaction.setDateTrans(Instant.now());
                        transaction.setSociete(soc);
                        transaction.setPrice(soc.getPrice() * (1 + (Math.random() * 12 - 6) / 100));
                        return transaction;
                    }));
                    return Flux.zip(interval, transactionFlux)
                            .map(data -> data.getT2())
                            .share()
                            ;
                });
    }

    @GetMapping(value = "/events/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Double> events(@PathVariable String id) {
        WebClient webClient = WebClient.create("http://localhost:8082");
        Flux<Double> eventFlux = webClient.get()
                .uri("/streamEvent/" + id)
                .retrieve()
                .bodyToFlux(Event.class)
                .map(data -> data.getPrice());
        return eventFlux;
    }
}

@Data
class Event {
    private Instant instant;
    private double price;
    private String societeID;
}