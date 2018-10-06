package cn.caojiantao.husky.controller;

import cn.caojiantao.husky.dto.TestDTO;
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

    @Cacheable(cacheNames = "test")
    @RequestMapping("/test")
    public TestDTO test(String name) {
        return new TestDTO(name);
    }
}
