package com.example.tasks;

import com.common.Person;
import com.example.repository.PersonRepository;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.TypedLambdaFunction;
import reactor.core.publisher.Mono;

import java.util.Map;

@PreLoad(route = "v1.delete.person", instances = 100, isPrivate = false)
public class DeletePerson implements TypedLambdaFunction<Map<String, Object>, Mono<Object>> {
    @Override
    public Mono<Object> handleEvent(Map<String, String> headers, Map<String, Object> input, int instance) throws Exception {
        PersonRepository  personRepository = new PersonRepository();
        return personRepository.deletePerson(input.get("person_id").toString())
                .onErrorResume(error -> {
                    System.err.println("Error deleting person: " + error.getMessage());
                    return Mono.error(error);
                });
    }
}
