package cn.caojiantao.husky.controller;

import cn.caojiantao.husky.mapper.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author caojiantao
 * @date 2018/9/29 16:54
 */
@RestController
@RequestMapping("")
public class TestController {

    @Autowired
    private Test test;

    @Cacheable(value = "test", key = "#p0")
    @RequestMapping("/test")
    public String test(String name) {
        return test.test();
    }
}
