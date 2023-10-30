package com.fakeperson.worker.service;

import com.fakeperson.worker.model.FakePerson;
import com.fakeperson.worker.repository.FakePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FakePersonService {
    private FakePersonRepository repository;
    @Autowired
    public FakePersonService(FakePersonRepository repository) {
        this.repository = repository;
    }

    public void addFakePerson(String name) {
        var person = new FakePerson();
        person.setName(name);
        repository.save(person);
    }
}
