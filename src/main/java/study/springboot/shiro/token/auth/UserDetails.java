package study.springboot.shiro.token.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserDetails {

    private String ubId;

    private Set<String> permissionSet;
}