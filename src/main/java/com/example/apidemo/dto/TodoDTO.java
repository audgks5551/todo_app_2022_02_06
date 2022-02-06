package com.example.apidemo.dto;

import com.example.apidemo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {

    private String id;
    private String title;
    private boolean done;
    // userid를 적지 않은 이유는 보안상 숨겨야하기 때문

    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO dto) {
        return TodoEntity.builder()
                .title(dto.getTitle())
                .done(dto.isDone())
                .id(dto.getId())
                .build();
    }
}
