package study.springboot.shiro.token.support.result;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result<T> {

    private String code;

    private String desc;

    private T data;

    public Result() {
        this("", "");
    }

    public Result(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
