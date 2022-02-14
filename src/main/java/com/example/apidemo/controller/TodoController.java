package com.example.apidemo.controller;

import com.example.apidemo.dto.ResponseDTO;
import com.example.apidemo.dto.TodoDTO;
import com.example.apidemo.model.TodoEntity;
import com.example.apidemo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO
                .<String>builder()
                .data(list)
                .build();
        return ResponseEntity.ok().body(response);
    }

    // 생성
    @PostMapping
    public ResponseEntity<?> createTodo(
            @AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto) {

        try {

            // dto --> entity
            TodoEntity entity = TodoDTO.toEntity(dto);

            // id를 null로 초기화 (생성 당시에는 id가 없어야 하기 때문)
            entity.setId(null);

            // @AuthenticationPrincipal에서 넘어온 userId
            entity.setUserId(userId);

            // 1. entity save
            // 2. userid로 찾은 개체들을 반환
            List<TodoEntity> entities = service.create(entity);

            // entity --> dto
            // entity 객체 하나 당 TodoDTO 객체를 하나씩 생성한 후 list에 담는다는 표현
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // build
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {

            // when error
            String error = e.getMessage();

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }

    // 전체 조회
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {

        // total todolist
        List<TodoEntity> entities = service.retrieve(userId);

        // entities --> dtos
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // build
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    // 수정
    @PutMapping
    public ResponseEntity<?> updateTodo(
            @AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto) {

        // dto --> entity
        TodoEntity entity = TodoDTO.toEntity(dto);

        entity.setUserId(userId);

        // 1. entity update
        // 2. total todolist 반환
        List<TodoEntity> entities = service.update(entity);

        // entities --> dtos
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // build
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);

    }

    // 삭제
    @DeleteMapping
    public ResponseEntity<?> deleteTodo(
            @AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto) {

        try {

            // dto --> entity
            TodoEntity entity = TodoDTO.toEntity(dto);

            entity.setUserId(userId);

            // 1. delete
            // 2. retrieve
            List<TodoEntity> entities = service.delete(entity);

            // entities --> dtos
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {

            String error = e.getMessage();

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }
}
