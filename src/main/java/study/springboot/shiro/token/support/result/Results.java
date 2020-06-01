package study.springboot.shiro.token.support.result;

import com.google.common.collect.Maps;

public final class Results {

    private Results() {
    }

    public static Result success() {
        return success(null);
    }

    public static <T> Result success(T data) {
        if (data == null) {
            data = (T) Maps.newHashMap();
        }
        Result rst = new Result(data);
        return rst;
    }
}
