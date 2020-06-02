package study.springboot.shiro.token.auth;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;

//@Component("securitySource")
public class SecuritySourceFactoryBean implements FactoryBean<ArrayList<String>> {

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public ArrayList<String> getObject() throws Exception {
        ArrayList<String> permissionLt = Lists.newArrayList();
        permissionLt.add("/res/add");
        permissionLt.add("/res/modify");
        return permissionLt;
    }

    @Override
    public Class<?> getObjectType() {
        return ArrayList.class;
    }
}
