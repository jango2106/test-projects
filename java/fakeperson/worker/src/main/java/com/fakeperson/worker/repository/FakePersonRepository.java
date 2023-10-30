package com.fakeperson.worker.repository;

import com.fakeperson.worker.model.FakePerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FakePersonRepository extends JpaRepository<FakePerson, Long> {}
