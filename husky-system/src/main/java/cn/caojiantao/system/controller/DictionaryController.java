package cn.caojiantao.system.controller;

import cn.caojiantao.system.model.dictionary.Dictionary;
import cn.caojiantao.system.query.DictionaryQuery;
import cn.caojiantao.system.service.IDictionaryService;
import com.github.caojiantao.dto.ResultDTO;
import com.github.caojiantao.util.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/system/dictionary")
public class DictionaryController {

    private final IDictionaryService dictionaryService;

    @Autowired
    public DictionaryController(IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/getDictionaryByPage")
    public ResultDTO getDictionaryByPage(DictionaryQuery query) {
        int total = dictionaryService.countDictionaries(query);
        List<Dictionary> dictionaries = null;
        if (total > 0) {
            dictionaries = dictionaryService.getDictionaries(query);
        }
        return ResultDTO.success(JSONUtils.toPageData(dictionaries, total));
    }

    @PostMapping("/saveDictionary")
    public ResultDTO saveDictionary(@RequestBody Dictionary dictionary) {
        Integer id = dictionary.getId();
        boolean operate;
        if (id == null || id == 0) {
            operate = dictionaryService.addDictionary(dictionary);
        } else {
            operate = dictionaryService.updateDictionary(dictionary);
        }
        return operate ? ResultDTO.success() : ResultDTO.failure("保存字典失败");
    }

    @GetMapping("/getDictionaryById")
    public ResultDTO getDictionaryById(int id) {
        return ResultDTO.success(dictionaryService.getDictionaryById(id));
    }

    @PostMapping("/deleteDictionaryById")
    public ResultDTO deleteDictionaryById(@RequestBody Dictionary dictionary) {
        return dictionaryService.deleteDictionaryById(dictionary.getId()) ? ResultDTO.success() : ResultDTO.failure("删除字典失败");
    }

    @GetMapping("/getDictionariesByKeyword")
    public ResultDTO getDictionariesByKeyword(String keyword) {
        DictionaryQuery query = new DictionaryQuery();
        query.setName(keyword);
        return ResultDTO.success(dictionaryService.getDictionaries(query));
    }
}
