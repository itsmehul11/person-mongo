package com.example.tasks;

import com.common.Person;
import com.example.repository.PersonRepository;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.TypedLambdaFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

@PreLoad(route = "v1.save.person", instances = 100, isPrivate = false)
public class SavePerson implements TypedLambdaFunction<Person, Mono<Object>> {
    PersonRepository  personRepository = new PersonRepository();
    @Override
    public Mono<Object> handleEvent(Map<String, String> headers, Person input, int instance) throws Exception {
        return personRepository.savePerson(input)
                .onErrorResume(error -> {
                    System.err.println("Error saving person: " + error.getMessage());
                    return Mono.error(error);
                });
    }
}
