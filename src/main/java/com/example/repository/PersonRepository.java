package com.example.repository;

import com.common.Person;
import com.example.config.MongoDBConfig;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final MongoCollection<Document> collection;

    public PersonRepository() {
        this.collection = MongoDBConfig.getDatabase().getCollection("person");
    }

    public Mono<Person> findPersonById(String id) {
        return Mono.from(collection.find(new Document("_id", id)).first())
                .map(this::mapToPerson)
                .switchIfEmpty(Mono.error(new RuntimeException("Person not found")));
    }

    private Person mapToPerson(Document doc) {
        Person person = new Person();
        person.setId(Integer.parseInt(doc.getString("_id")));
        person.setName(doc.getString("name"));
        person.setAddress(doc.getString("address"));
        return person;
    }

    public Mono<Object> savePerson(Person person) {
        Document document = new Document();
        document.append("_id", person.getId().toString());
        document.append("name", person.getName());
        document.append("address", person.getAddress());
        return Mono.from(collection.insertOne(document))
                .map(result -> "Person " + person.getName() + " saved successfully");
    }

    public Mono<Object> updatePerson(Person input) {
        Document query = new Document("_id", input.getId().toString());
        Document update = new Document("$set", new Document("name", input.getName()).append("address", input.getAddress()));
        return Mono.from(collection.updateOne(query, update))
                .map(result -> "Person " + input.getName() + " updated successfully");
    }

    public Mono<Object> deletePerson(String id) {
        Document query = new Document("_id", id);
        return Mono.from(collection.deleteOne(query))
                .map(result -> "Person with ID " + id + " deleted successfully");
    }


    public List<Person> findAllPersons() {
        List<Person> persons = Flux.from(collection.find()).map(this::mapToPersons).collectList().block();
        System.out.println( persons + " all persons in find all persons method");
        return Flux.from(collection.find()).map(this::mapToPersons).collectList().block();

    }

    public Person mapToPersons(Document doc) {
        Person person = new Person();
        person.setId(Integer.parseInt(doc.getString("_id")));
        person.setName(doc.getString("name"));
        person.setAddress(doc.getString("address"));
        System.out.println(person.toString() + " map to persons");
        return  person;
    }

}
