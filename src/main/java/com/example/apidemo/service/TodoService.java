package com.example.apidemo.service;

import com.example.apidemo.model.TodoEntity;
import com.example.apidemo.persistence.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// todolist는 CRUD를 하고 항상 전체조회를 반환해야 한다.
@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repository;


    public String testService() {

        TodoEntity entity = TodoEntity.builder()
                .title("My first todo item")
                .build();

        repository.save(entity);

        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    // 검증
    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    // 생성
    public List<TodoEntity> create(final TodoEntity entity) {

        validate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());

    }

    // 전체 조회
    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    // 수정
    public List<TodoEntity> update(final TodoEntity entity) {

        validate(entity);
            
        // Optional을 사용한 이유는 NULL이 나올 수도 있기 때문에
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        // ifPresent는 original이 null이 아니라면 다음 로직 실행
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    // 삭제
    public List<TodoEntity> delete(final TodoEntity entity) {

        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());
    }

}
