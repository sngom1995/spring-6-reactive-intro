package sn.guru.springframework.spring6reactiveexamples.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sn.guru.springframework.spring6reactiveexamples.domain.Person;

public class PersonRepositoryImpl implements PersonRepository {

    Person samba = Person.builder().id(1).firstName("Samba").lastName("Gaye").build();
    Person moussa = Person.builder().id(2).firstName("Moussa").lastName("Ngom").build();
    Person abdou = Person.builder().id(3).firstName("Abdou").lastName("Seye").build();
    Person mame = Person.builder().id(4).firstName("Mame").lastName("Diop").build();
    Person fatou = Person.builder().id(5).firstName("Fatou").lastName("Fall").build();
    Person aminata = Person.builder().id(6).firstName("Aminata").lastName("Sow").build();
    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(samba);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(samba, moussa, abdou, mame, fatou, aminata);
    }
}
