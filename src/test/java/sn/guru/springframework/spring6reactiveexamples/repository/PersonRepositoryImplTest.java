package sn.guru.springframework.spring6reactiveexamples.repository;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import sn.guru.springframework.spring6reactiveexamples.domain.Person;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();
    @Test
    void testMonByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);
        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void testMonoByIdSubscriber() {

        Mono<Person> personMono = personRepository.getById(1);
        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapOperation() {
        Mono<Person> person = personRepository.getById(1);
        person.publishOn(Schedulers.boundedElastic()).map(person1 -> {
            return Objects.requireNonNull(person.block()).getFirstName();
        }).subscribe(System.out::println);
    }

    @Test
    void name() {
    }
}
