package study.springboot.shiro.token.service.popedom;

import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PopedomServiceImpl implements PopedomService {

    @Override
    public List<String> getFunctionLt() {
        List<String> data = Lists.newArrayList();
        data.add("/res/add");
        data.add("/res/modify");
        data.add("/res/list");
        return data;
    }

    @Override
    public List<String> getFunctionLt(Long userId) {
        List<String> data = Lists.newArrayList();
        data.add("/res/add");
        return data;
    }
}
