package com.fakeperson.client.repository;


import com.fakeperson.client.model.FakePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FakePersonRepository extends JpaRepository<FakePerson, Long> {}
