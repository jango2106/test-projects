package com.fakeperson.client.controller;

import com.fakeperson.client.model.FakePerson;
import com.fakeperson.client.service.FakePersonService;
import com.fakeperson.client.service.PeopleGeneratorApiService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "fake/people")
public class FakePersonController {

    private PeopleGeneratorApiService apiService;
    private FakePersonService personService;
    private RabbitTemplate rabbitTemplate;

    public FakePersonController(FakePersonService personService, PeopleGeneratorApiService peopleGeneratorApiService, RabbitTemplate rabbitTemplate) {
        this.apiService = peopleGeneratorApiService;
        this.rabbitTemplate = rabbitTemplate;
        this.personService = personService;
    }
    @PostMapping(path = "/{count}", produces = "application/json")
    public void createPeople(@PathVariable int count) throws IOException, InterruptedException {
        var people = apiService.getRandomPeople(count);
        System.out.println("Count: " + people.size());
        people.forEach(it -> rabbitTemplate.convertAndSend("fakePersonQueue", it.name()));
    }

    @GetMapping(path = "/", produces = "application/json")
    public List<FakePerson> getPeople() {
        return personService.getAllFakePeople();
    }
}
