package study.springboot.shiro.token.support.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public final class Result<T> implements Serializable {

    private static final String SUCC_CODE = "0000";

    private static final String SUCC_DESC = "成功";

    private String code;

    private String desc;

    private T data;

    public Result(T data) {
        this(SUCC_CODE, SUCC_DESC);
        this.data = data;
    }

    public Result(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
