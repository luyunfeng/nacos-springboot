package tech.soulcoder.nacos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunfeng.lu
 * @create 2019/1/26.
 */
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {
    @Value("${dept}")
    String dept;

    @Value("${id}")
    String id;

    @GetMapping("/healthChecker")
    public String healthChecker() {
        return dept + "-" + id;
    }


}
