package com.example.tasks;

import com.common.Person;
import com.example.repository.PersonRepository;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.TypedLambdaFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

@PreLoad(route = "v1.update.person", instances = 100, isPrivate = false)
public class UpdatePerson implements TypedLambdaFunction<Person, Mono<Object>> {
    @Override
    public Mono<Object> handleEvent(Map<String, String> headers, Person input, int instance) throws Exception {
        PersonRepository personRepository = new PersonRepository();

        return personRepository.updatePerson(input)
                .onErrorResume(error -> {
                    System.err.println("Error updating person: " + error.getMessage());
                    return Mono.error(error);
                });
    }
}
