package study.springboot.shiro.token.service.popedom;

import java.util.List;

public interface PopedomService {

    List<String> getFunctionLt();

    List<String> getFunctionLt(Long userId);
}
