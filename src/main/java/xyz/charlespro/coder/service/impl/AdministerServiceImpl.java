package xyz.charlespro.coder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.charlespro.coder.mapper.AdministerMapper;
import xyz.charlespro.coder.mapper.HrMapper;
import xyz.charlespro.coder.mapper.UserMapper;
import xyz.charlespro.coder.pojo.Administer;
import xyz.charlespro.coder.service.AdministerService;

@Service
public class AdministerServiceImpl implements AdministerService {
    @Autowired
    AdministerMapper administerMapper;
    UserMapper userMapper;
    HrMapper hrMapper;
    @Override
    public boolean login(int id, String password) {
        Administer administer=administerMapper.selectOne(new QueryWrapper<Administer>().eq("id",id));
        return password.equals(administer.getPassword());
    }

}
