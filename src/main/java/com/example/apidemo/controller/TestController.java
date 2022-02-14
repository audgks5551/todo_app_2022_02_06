package com.example.apidemo.controller;

import com.example.apidemo.dto.ResponseDTO;
import com.example.apidemo.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// REST 제약조건
// 1. 클라이언트-서버
// 2. 상태가 없는 (stateless)
// 3. 캐시되는 데이터
// 4. 일관적인 인터페이스
// 5. 레이어 시스템
// 6. 코드-온-디맨드
@RestController // JSON을 리턴하는 웹 서비스임을 명시
// @RestController안에 @ResponseBody가 있는데 객체를 JSON형식으로 바꾸고 HttpResponse에 담아 반환한다. 이것을 직렬화라고 한다.
@RequestMapping("test")
public class TestController {

    @GetMapping
    public String testController() {
        return "hello world!";
    }

    @GetMapping("/testGetMapping") // path 설정, GET메서드 사용
    public String testControllerWithPath() {
        return "hello world! testGetMapping";
    }

    @GetMapping("/{id}") // @PathVariable를 이용하면 http://localhost/test/1 이면 1을 받아 매개변수 id에 들어간다
                            // required = false는 꼭 들어오지 않아도 된다는 뜻이다. 에러가 나지 않는다.
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "Hello World! ID" + id;
    }

    // @RequestParam은 localhost/testRequestParam?id=2 에서 id의 숫자 2를 가져와 매개변수에 넣는다
    @GetMapping("/testRequestParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "Hello World! ID " + id;
    }

    // @RequestBody는 바디에서 적힌 것을 객체 형식으로 받을 수 있다. (예) JSON형식을 TestRequestBodyDTO로 변환하여 가져옴)
    @GetMapping("/testRequestBody")
    public String testControllerRequestBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "Hello World! ID" + testRequestBodyDTO.getId() + " Message : " + testRequestBodyDTO.getMessage();
    }

    // org.springframework.http.ResponseEntity에서 지원
    // header, status, body 조작 가능
    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseDTO");
        ResponseDTO<String> response = ResponseDTO
                .<String>builder()
                .data(list)
                .build();
        return response;
    }

    //
    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity() {
        List<String> list = new ArrayList<>();
        list.add("Hello World! I'm ResponseEntity. And you got 400!");
        ResponseDTO<String> response = ResponseDTO
                .<String>builder()
                .data(list)
                .build();
        return ResponseEntity.ok().body(response);
    }
}
