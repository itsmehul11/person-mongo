package com.example.tasks;

import com.common.Person;
import com.example.config.MongoDBConfig;
import com.example.repository.PersonRepository;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.LambdaFunction;
import org.platformlambda.core.models.TypedLambdaFunction;
import reactor.core.publisher.Flux;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PreLoad(route = "person.all", instances = 100, isPrivate = false)
public class GetAllPersons implements LambdaFunction {

    @Override
    public List<Person> handleEvent(Map<String, String> headers, Object input, int instance) throws Exception {
        PersonRepository personRepository = new PersonRepository();

        List<Person> allPersons = personRepository.findAllPersons();
        System.out.println(allPersons.toString()+ " all persons controller");
        return allPersons;
    }


}
