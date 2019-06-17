package com.info.org.sid.web;

import com.info.org.sid.dao.SocieteRepository;
import com.info.org.sid.entities.Societe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin("*")
@RestController
public class SocieteRestFullReactiveController {
    @Autowired
    SocieteRepository societeRepository;
    @GetMapping(value= "/societes")

    public Flux<Societe> findAll(){
        return  societeRepository.findAll();
    }

    @GetMapping(value= "/societes/{id}")
    public Mono<Societe> findOne(@PathVariable String id){
        return  societeRepository.findById(id);
    }

    @PostMapping("/societes")
    public Mono<Societe> save(@RequestBody Societe s){
        return  societeRepository.save(s);
    }

    @DeleteMapping(value= "/societes/{id}")
    public Mono<Void> delete(@PathVariable String id){
        return  societeRepository.deleteById(id);
    }


    @PutMapping(value= "/societes/{id}")
    public Mono<Societe> update(@RequestBody Societe s, @PathVariable String id){
        s.setId(id);
        return  societeRepository.save(s);
    }
}
