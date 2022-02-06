package com.example.apidemo.service;

import com.example.apidemo.model.TodoEntity;
import com.example.apidemo.persistence.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    public String testService() {

        TodoEntity entity = TodoEntity.builder()
                .title("My first todo item")
                .build();

        repository.save(entity);

        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }
}
