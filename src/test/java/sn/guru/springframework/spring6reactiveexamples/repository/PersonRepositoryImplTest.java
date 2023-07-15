package sn.guru.springframework.spring6reactiveexamples.repository;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import sn.guru.springframework.spring6reactiveexamples.domain.Person;

import java.util.List;
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
    void testFluxBlockFirst() {
        Flux<Person> personFlux = personRepository.findAll();
        Person person = personFlux.blockFirst();
        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMap() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxMonoList(){
        Flux<Person> personFlux = personRepository.findAll();
        Mono<List<Person>> personMono = personFlux.collectList();
        personMono.subscribe(list -> {
            list.forEach(person -> {
                System.out.println(person.toString());
            });
        });
    }

    @Test
    void testFluxFilter() {
        Flux<Person> personFlux = personRepository.findAll();
        personFlux.filter(person -> person.getFirstName().equalsIgnoreCase("samba"))
                .subscribe(person -> {
                    System.out.println(person.toString());
                });
    }

    @Test
    void testGetById() {
        Mono<Person> personMono = personRepository.findAll().filter(person -> person.getFirstName().equals("Samba"))
                .next();
        personMono.subscribe(person -> {
            System.out.println(person.getFirstName());
        });
        }

    @Test
    void testPersonNotFoundById() {
        Mono<Person> personFlux = personRepository.findAll().filter(person -> person.getId() == 8).single()
                .doOnError(throwable -> {
                    System.out.println("Error occured");
                    System.out.println(throwable.getMessage());
                });
        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("No person found");
            System.out.println(throwable.getMessage());
        });
    }
}
