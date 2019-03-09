package cn.caojiantao.husky.resource.service.impl;

import cn.caojiantao.husky.resource.entity.People;
import cn.caojiantao.husky.resource.mapper.PeopleMapper;
import cn.caojiantao.husky.resource.service.IPeopleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caojiantao
 * @since 2019-03-08
 */
@Service
public class PeopleServiceImpl extends ServiceImpl<PeopleMapper, People> implements IPeopleService {

}
