package study.springboot.shiro.token.support.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public final class Result<T> implements Serializable {

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
