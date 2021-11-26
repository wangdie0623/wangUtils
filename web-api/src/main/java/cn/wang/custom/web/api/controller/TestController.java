package cn.wang.custom.web.api.controller;



import cn.wang.custom.utils.BusinessIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 王叠 2019-05-05 15:32
 */
@Slf4j
@RestController
public class TestController {
   private ExecutorService pool = Executors.newFixedThreadPool(10);
    @GetMapping("/test")
    public String hello(){
        String mark = BusinessIdUtils.getMark();
        pool.execute(()->{
            BusinessIdUtils.forwardMark(mark);
            log.info("新线程");
        });
        return "hello world";
    }
}
