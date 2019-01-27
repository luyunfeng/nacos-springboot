package tech.soulcoder.nacos;

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
public class TestController {

    @GetMapping("/healthChecker")
    public String healthChecker(){
        return "ok";
    }
}
