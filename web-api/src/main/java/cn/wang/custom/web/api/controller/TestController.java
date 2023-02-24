package cn.wang.custom.web.api.controller;


import cn.wang.custom.utils.BusinessIdUtils;
import cn.wang.custom.web.api.beans.JsonResult;
import cn.wang.custom.web.api.dto.DtoMsgReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@RestController
public class TestController {
   private ExecutorService pool = Executors.newFixedThreadPool(10);

    @GetMapping("/test")
    public String hello() {
        String mark = BusinessIdUtils.getMark();
        pool.execute(() -> {
            BusinessIdUtils.forwardMark(mark);
            log.info("新线程");
        });
        return "hello world";
    }

    @PostMapping("/errorTest")
    @ResponseBody
    public String errorTest(DtoMsgReq msgReq) {

        return JsonResult.ok(null);
    }
}
