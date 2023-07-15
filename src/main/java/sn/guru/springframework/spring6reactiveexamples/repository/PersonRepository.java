package sn.guru.springframework.spring6reactiveexamples.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.guru.springframework.spring6reactiveexamples.domain.Person;

public interface PersonRepository {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();

}
