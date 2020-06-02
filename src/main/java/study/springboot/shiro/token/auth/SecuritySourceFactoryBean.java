package study.springboot.shiro.token.auth;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import study.springboot.shiro.token.service.popedom.PopedomService;

import java.util.ArrayList;
import java.util.List;

/**
 * FactoryBean<T>泛型，使用List报错，使用ArrayList正常
 */
@Component
public class SecuritySourceFactoryBean implements FactoryBean<ArrayList<String>> {

    @Autowired
    private PopedomService popedomService;

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public ArrayList<String> getObject() throws Exception {
        //获取
        List<String> data = popedomService.getFunctionLt();
        //
        ArrayList<String> securitySource = Lists.newArrayList();
        data.forEach(e -> {
            securitySource.add(e);
        });
        return securitySource;
    }

    @Override
    public Class<?> getObjectType() {
        return ArrayList.class;
    }
}
