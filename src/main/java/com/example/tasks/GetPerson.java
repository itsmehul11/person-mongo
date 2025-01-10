package com.example.tasks;

import com.common.Person;
import com.example.repository.PersonRepository;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.TypedLambdaFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

@PreLoad(route = "person.pojo", instances = 100, isPrivate = false)
public class GetPerson implements TypedLambdaFunction<Map<String, Object>, Mono<Person>> {

    @Override
    public Mono<Person> handleEvent(Map<String, String> headers, Map<String, Object> input, int instance) throws Exception {
        PersonRepository personRepository = new PersonRepository();

        String personId = input.get("person_id").toString();

        if (personId == null || personId.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Person ID cannot be null or empty"));
        }

        return personRepository.findPersonById(personId)
                .onErrorResume(error -> {
                    System.err.println("Error retrieving person: " + error.getMessage());
                    return Mono.error(error);
                });
    }
}
