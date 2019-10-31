package cn.caojiantao.husky.system.controller;

import cn.caojiantao.husky.system.model.dictionary.SystemDictionary;
import cn.caojiantao.husky.system.query.DictionaryQuery;
import cn.caojiantao.husky.system.service.SystemDictionaryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author caojiantao
 */
@RestController
@RequestMapping("/system/dictionary")
public class SystemDictionaryController extends ApiController {

    private final SystemDictionaryService systemDictionaryService;

    @Autowired
    public SystemDictionaryController(SystemDictionaryService systemDictionaryService) {
        this.systemDictionaryService = systemDictionaryService;
    }

    @GetMapping("/getDictionaryByPage")
    public R getDictionaryByPage(DictionaryQuery query) {
        QueryWrapper<SystemDictionary> wrapper = Wrappers.query();
        wrapper.like("name", query.getName());
        return success(systemDictionaryService.page(query, wrapper));
    }

    @PostMapping("/saveDictionary")
    public R saveDictionary(@RequestBody SystemDictionary systemDictionary) {
        Integer id = systemDictionary.getId();
        boolean operate;
        if (id == null || id == 0) {
            systemDictionary.setGmtCreate(LocalDateTime.now());
            operate = systemDictionaryService.save(systemDictionary);
        } else {
            systemDictionary.setGmtModified(LocalDateTime.now());
            operate = systemDictionaryService.updateById(systemDictionary);
        }
        return operate ? success(null) : failed("保存字典失败");
    }

    @GetMapping("/getDictionaryById")
    public R getDictionaryById(int id) {
        return success(systemDictionaryService.getDictionaryById(id));
    }

    @PostMapping("/deleteDictionaryById")
    public R deleteDictionaryById(@RequestBody SystemDictionary systemDictionary) {
        return systemDictionaryService.removeById(systemDictionary.getId()) ? success(null) : failed("删除字典失败");
    }

    @GetMapping("/getDictionariesByKeyword")
    public R getDictionariesByKeyword(String keyword) {
        QueryWrapper<SystemDictionary> wrapper = Wrappers.query();
        wrapper.eq("name", keyword);
        return success(systemDictionaryService.list(wrapper));
    }
}
