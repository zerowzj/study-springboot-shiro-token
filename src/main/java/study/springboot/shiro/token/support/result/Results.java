package study.springboot.shiro.token.support.result;

import com.google.common.collect.Maps;

public final class Results {

    private Results() {
    }

    public static Result success() {
        return success(null);
    }

    public static <T> Result success(T data) {
        Result rst = new Result();
        if (data != null) {
            rst.setData(data);
        } else {
            rst.setData(Maps.newHashMap());
        }
        return rst;
    }
}
