package com.fakeperson.client.service;

import com.fakeperson.client.model.FakePerson;
import com.fakeperson.client.repository.FakePersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FakePersonService {
    private FakePersonRepository repository;
    @Autowired
    public FakePersonService(FakePersonRepository repository) {
        this.repository = repository;
    }

    public List<FakePerson> getAllFakePeople() {
        return repository.findAll();
    }
}
