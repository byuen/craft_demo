package com.byuen.service;

import com.byuen.model.Person;
import com.byuen.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person personRequest) {
        return personRepository.save(personRequest);
    }
}
