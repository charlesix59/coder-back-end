package xyz.charlespro.coder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.charlespro.coder.mapper.HrMapper;
import xyz.charlespro.coder.pojo.Hr;
import xyz.charlespro.coder.service.HrService;
import xyz.charlespro.coder.utils.JwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HrServiceImpl implements HrService {
    @Autowired(required = false)
    HrMapper hrMapper;
    Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    @Override
    public Map<String, Object> login(String email, String password) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        Hr hr=hrMapper.selectOne(new QueryWrapper<Hr>().eq("email",email));
        if (hr==null){
            resMap.put("msg","invalid account");
            resMap.put("success",false);
            return resMap;
        }
        boolean res = passwordEncoder.matches(password, hr.getPassword());
        if (res){
            resMap.put("success",true);
            resMap.put("token", JwtUtils.createJWT(hr.getId()));
            System.out.println(hr.getId());
        }
        else{
            resMap.put("success",false);
            resMap.put("msg","wrong password");
        }
        return resMap;
    }

    @Override
    public boolean register(Hr hr) {
        hr.setPassword(passwordEncoder.encode(hr.getPassword()));
        hr.setId(0);
        hr.setCompanyId(1);
        return hrMapper.insert(hr)>0;
    }

    @Override
    public Boolean resetPassword(Integer id, String oldPassword, String newPassword) {
        Hr oldHr=hrMapper.selectOne(new QueryWrapper<Hr>().eq("id",id));
        if (oldHr==null){
            return false;
        }
        if(!passwordEncoder.matches(oldPassword,oldHr.getPassword())){
            return false;
        }
        oldHr.setPassword(passwordEncoder.encode(newPassword));
        return hrMapper.updateById(oldHr)>0;
    }

    @Override
    public boolean update(Hr hr) {
        return hrMapper.updateById(hr)>0;
    }

    @Override
    public boolean ModifyPersonalData(Integer id, boolean gender, Date birthday) {
        Hr hr = hrMapper.selectById(id);
        if (hr==null){
            return false;
        }
        hr.setGender(gender);
        hr.setBirthday(birthday);
        return hrMapper.updateById(hr)>0;
    }

    @Override
    public Hr getHrById(int id) {
        return hrMapper.selectOne(new QueryWrapper<Hr>().eq("id",id));
    }

    @Override
    public Hr getHrByName(String name) {
        return hrMapper.selectOne(new QueryWrapper<Hr>().eq("name",name));
    }

    @Override
    public Hr getHrByEmail(String email) {
        return hrMapper.selectOne(new QueryWrapper<Hr>().eq("email",email));
    }

    @Override
    public Hr getHrByPhone(String phone) {
        return hrMapper.selectOne(new QueryWrapper<Hr>().eq("phone",phone));
    }

    @Override
    public List<Hr> getAllUser() {
        return hrMapper.selectList(null);
    }
    @Override
    public void deleteHr(int id) {
        hrMapper.deleteById(id);
    }















}

