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
    
    // @Autowired 사용하면 안됨(@Component를 적어야 함) DTO의 기능을 잃어버림
    // 왜냐하면 @Component를 하는 순간 싱글톤이 되기 때문에
    // DTO는 각 요청마다 생성되어야한다.
    // entity에 final로 선언하면 entity에 다른 참조 값을 지정할 수 없습니다.(객체의 속성은 변경 가능)
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
