package study.springboot.shiro.token.support.session;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class UserInfo implements Serializable {

    private Long uaId;
}
