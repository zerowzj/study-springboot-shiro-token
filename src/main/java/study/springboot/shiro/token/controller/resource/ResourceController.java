package study.springboot.shiro.token.controller.resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.springboot.shiro.token.support.result.Result;
import study.springboot.shiro.token.support.result.Results;

@RestController
@RequestMapping("/res")
public class ResourceController {

    @PostMapping("/list")
    public Result list() {
        return Results.success();
    }

    @PostMapping("/add")
    public Result add() {
        return Results.success();
    }

    @PostMapping("/modify")
    public Result modify() {
        return Results.success();
    }
}
