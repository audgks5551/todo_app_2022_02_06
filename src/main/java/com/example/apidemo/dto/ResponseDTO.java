package com.example.apidemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    private String error;
    private List<T> data;
}

// 제네릭은 요소를 찾아올 때는 반드시 String으로 타입 변환
// Java에서 제네릭 타입을 사용할 경우 컴파일 과정에서 타입체크를 할 수 있습니다.
// 클래스와 메소드를 선언할때 파라미터로 선언할 수 있습니다.
