package com.yi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/main")
public class MainController {

    // 값 리턴 시에 responsebody를 달아 준다
    @GetMapping("/")
    // Body가 있으면 return되는 값을 화면에 출력 또는 프론트에 값을 넘겨준다.
    @ResponseBody
    public String main(){
        return "Hello World";
    }

    // 페이지 매핑 시 string+ '.html' 파일을 찾아 랜더링해줌
    @GetMapping("/board")
    public String boardWriteForm(){
        return "boardWrite";
    }
}