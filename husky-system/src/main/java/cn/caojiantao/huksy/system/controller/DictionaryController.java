package cn.caojiantao.huksy.system.controller;

import cn.caojiantao.huksy.system.query.DictionaryQuery;
import cn.caojiantao.huksy.system.model.dictionary.Dictionary;
import cn.caojiantao.huksy.system.service.DictionaryService;
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
public class DictionaryController extends ApiController {

    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/getDictionaryByPage")
    public R getDictionaryByPage(DictionaryQuery query) {
        QueryWrapper<Dictionary> wrapper = Wrappers.query();
        wrapper.like("name", query.getName());
        return success(dictionaryService.page(query, wrapper));
    }

    @PostMapping("/saveDictionary")
    public R saveDictionary(@RequestBody Dictionary dictionary) {
        Integer id = dictionary.getId();
        boolean operate;
        if (id == null || id == 0) {
            dictionary.setGmtCreate(LocalDateTime.now());
            operate = dictionaryService.save(dictionary);
        } else {
            dictionary.setGmtModified(LocalDateTime.now());
            operate = dictionaryService.updateById(dictionary);
        }
        return operate ? success(null) : failed("保存字典失败");
    }

    @GetMapping("/getDictionaryById")
    public R getDictionaryById(int id) {
        return success(dictionaryService.getDictionaryById(id));
    }

    @PostMapping("/deleteDictionaryById")
    public R deleteDictionaryById(@RequestBody Dictionary dictionary) {
        return dictionaryService.removeById(dictionary.getId()) ? success(null) : failed("删除字典失败");
    }

    @GetMapping("/getDictionariesByKeyword")
    public R getDictionariesByKeyword(String keyword) {
        QueryWrapper<Dictionary> wrapper = Wrappers.query();
        wrapper.eq("name", keyword);
        return success(dictionaryService.list(wrapper));
    }
}
