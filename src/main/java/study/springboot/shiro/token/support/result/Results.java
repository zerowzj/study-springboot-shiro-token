package study.springboot.shiro.token.support.result;

public class Results {

    public static Result success() {
        Result rst = new Result();
        return rst;
    }

    public static <T> Result success(T data) {
        Result rst = new Result();
        rst.setData(data);
        return rst;
    }
}
