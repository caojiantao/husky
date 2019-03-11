package cn.caojiantao.husky.resource.controller;

import cn.caojiantao.husky.common.service.QiniuService;
import cn.caojiantao.husky.resource.entity.People;
import cn.caojiantao.husky.resource.query.PeopleQuery;
import cn.caojiantao.husky.resource.service.IPeopleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author caojiantao
 * @since 2019-03-08
 */
@RestController
@RequestMapping("/resource/people")
public class PeopleController extends ApiController {

    private final IPeopleService peopleService;
    private final QiniuService qiniuService;

    @Autowired
    public PeopleController(IPeopleService peopleService, QiniuService qiniuService) {
        this.peopleService = peopleService;
        this.qiniuService = qiniuService;
    }

    @PostMapping("/savePeople")
    public R savePeople(@RequestBody People people) {
        people.setAvatar(qiniuService.simplifyUrl(people.getAvatar()));
        return peopleService.saveOrUpdate(people) ? success(null) : failed("保存人员失败");
    }

    @PostMapping("/deletePeopleById")
    public R deletePeopleById(@RequestBody People people) {
        return peopleService.removeById(people.getId()) ? success(null) : failed("删除人员失败");
    }

    @GetMapping("/getPeopleByPage")
    public R getPeopleByPage(PeopleQuery query) {
        QueryWrapper<People> wrapper = Wrappers.query();
        wrapper.like("name", query.getName());
        return success(peopleService.page(query, wrapper));
    }

    @GetMapping("/getPeopleById")
    public R getPeopleById(Integer id) {
        People people = peopleService.getById(id);
        people.setAvatar(qiniuService.decorateUrl(people.getAvatar()));
        return success(people);
    }
}
