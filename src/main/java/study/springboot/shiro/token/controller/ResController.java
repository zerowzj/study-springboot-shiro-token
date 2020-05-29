package study.springboot.shiro.token.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.shiro.token.support.result.Result;
import study.springboot.shiro.token.support.result.Results;

import java.util.Map;

@RestController
@RequestMapping("/res")
public class ResController {

    @PostMapping("/list")
    public Result list() {
        return Results.success();
    }
}
