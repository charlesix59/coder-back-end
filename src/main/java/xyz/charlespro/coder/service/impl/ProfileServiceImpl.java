package xyz.charlespro.coder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.charlespro.coder.mapper.ProfileMapper;
import xyz.charlespro.coder.pojo.Profile;
import xyz.charlespro.coder.pojo.User;
import xyz.charlespro.coder.service.ProfileService;

import java.util.Date;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    ProfileMapper profileMapper;
    @Override
    public boolean updateIsPublic(Profile profile) {
        return profileMapper.updateById(profile)>0;
    }

    @Override
    public boolean addProfile(Profile profile) {
        return profileMapper.insert(profile)>0;
    }

    @Override
    public boolean remove(Profile profile) {
        return profileMapper.deleteById(profile.getId())>0;
    }

    @Override
    public Profile getProfileByUserId(Integer id) {
        return profileMapper.selectOne(new QueryWrapper<Profile>().eq("user_id",id));
    }

    @Override
    public Profile getProfileById(int id) {
        return profileMapper.selectOne(new QueryWrapper<Profile>().eq("id",id));
    }

    @Override
    public boolean uploadProfile(Profile profile) {
        return profileMapper.updateById(profile)>0;
    }


}
