package study.springboot.shiro.token.support.exception;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@RestController
public class GlobalErrorController implements ErrorController {

    private static final String ERROR_PATH = "redirect:/error";

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity error(HttpServletRequest request, HttpServletResponse response) {
        String requestId = request.getHeader("Request-Id");
        String uri = request.getRequestURI();
        log.info(" i am ErrorController!===>{},{}", requestId, uri);
        //
        int statusCode = ErrorUtils.getStatusCode(request);
        Exception exception = ErrorUtils.getException(request);
        String message = ErrorUtils.getMessage(request);

        log.info("message={}", message);
        Map<String, Object> data = Maps.newHashMap();
        if (statusCode == 500) {
            if(exception.getCause() instanceof UnauthorizedException){
                data.put("code", "4001");
                data.put("desc", "无权限");
            } else {
                data.put("code", "9999");
                data.put("desc", exception.getMessage());
            }
        } else if (statusCode == 404) {
            data.put("code", "9999");
            data.put("desc", "非法URL");
        }
        ResponseEntity entity = new ResponseEntity(data, HttpStatus.OK);
        return entity;
    }
}
