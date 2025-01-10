package com.example.tasks;

import com.common.Person;
import com.example.repository.PersonRepository;
import org.platformlambda.core.annotations.PreLoad;
import org.platformlambda.core.models.TypedLambdaFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@PreLoad(route = "v1.person.exception", instances = 100, isPrivate = false)
public class PersonExceptionHandler implements TypedLambdaFunction<Map<String, Object>, Map<String, Object>> {

    private static final Logger log = LoggerFactory.getLogger(PersonExceptionHandler.class);

    private static final String TYPE = "type";
    private static final String ERROR = "error";
    private static final String STATUS = "status";
    private static final String MESSAGE = "message";

    @Override
    public Map<String, Object> handleEvent(Map<String, String> headers, Map<String, Object> input, int instance) {
        log.info("User defined exception handler got {} {}", headers, input);
        if (input.containsKey(STATUS) && input.containsKey(MESSAGE)) {
            Map<String, Object> error = new HashMap<>();
            error.put(STATUS, input.get(STATUS));
            error.put(MESSAGE, input.get(MESSAGE));
            error.put(TYPE, ERROR);
            return error;
        } else {
            return Collections.emptyMap();
        }
    }
}