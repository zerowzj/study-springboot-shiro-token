package study.springboot.shiro.token.support.exception;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
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

    private static final String PATH_ERROR = "redirect:/error";

    @Override
    public String getErrorPath() {
        return PATH_ERROR;
    }

    @RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity errorData(HttpServletRequest request, HttpServletResponse response) {
        log.info("======> GlobalErrorController");
        String requestId = request.getHeader("Request-Id");
        String uri = request.getRequestURI();
        log.info(" i am ErrorController!===>{},{}", requestId, uri);
        int statusCode = getStatusCode(request);
        Exception ex = getException(request);
        String msg = getMessage(request);
        String ur = (String) request.getAttribute("raw_url");
        Map<String, Object> data = Maps.newHashMap();
        if (500 == statusCode) {
            data.put("code", "9999");
            data.put("desc", "系统异常");
        } else if (404 == statusCode) {
            data.put("code", "9999");
            data.put("desc", "非法URL");
        }
        ResponseEntity entity = new ResponseEntity(data, HttpStatus.OK);
        return entity;
    }

    private int getStatusCode(HttpServletRequest request) {
        int statusCode = (int) request.getAttribute("javax.servlet.error.status_code");
        return statusCode;
    }

    private String getMessage(HttpServletRequest request) {
        String msg = (String) request.getAttribute("javax.servlet.error.message");
        return msg;
    }

    private Exception getException(HttpServletRequest request) {
        Exception ex = (Exception) request.getAttribute("javax.servlet.error.exception");
        return ex;
    }

    @RequestMapping(value = PATH_ERROR, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorPage(HttpServletRequest request) {
        return new ModelAndView("globalError");
    }
//
//    private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
//        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
//        Map<String, Object> map = errorAttributes.getErrorAttributes(requestAttributes);
//        if (request.getAttribute("status") instanceof Integer) {
//            map.put("status", request.getAttribute("status"));
//        }
//        map.put("url", request.getRequestURL().toString());
//        map.putIfAbsent("path", request.getAttribute("raw_url"));
//        return map;
//    }

}
