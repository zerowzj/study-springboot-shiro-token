package study.springboot.shiro.token.auth;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * FactoryBean<T>泛型，使用List报错，使用ArrayList正常
 */
@Component
public class SecuritySourceFactoryBean implements FactoryBean<ArrayList<String>> {

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public ArrayList<String> getObject() throws Exception {
        ArrayList<String> securitySource = Lists.newArrayList();
        securitySource.add("/res/add");
        securitySource.add("/res/modify");
        return securitySource;
    }

    @Override
    public Class<?> getObjectType() {
        return ArrayList.class;
    }
}
