package xyz.charlespro.coder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.charlespro.coder.mapper.UserMapper;
import xyz.charlespro.coder.pojo.User;
import xyz.charlespro.coder.service.UserService;
import xyz.charlespro.coder.utils.JwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    UserMapper userMapper;
    Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
    @Override
    public Map<String, Object> login(String email, String password) {
        Map<String,Object> resMap = new HashMap<String,Object>();
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
        if (user==null){
            resMap.put("msg","invalid account");
            resMap.put("success",false);
            return resMap;
        }
        boolean res = passwordEncoder.matches(password,user.getPassword());
        if (res){
            resMap.put("success",true);
            resMap.put("token", JwtUtils.createJWT(user.getId()));
            System.out.println(user.getId());
        }
        else{
            resMap.put("success",false);
            resMap.put("msg","wrong password");
        }
        return resMap;
    }

    @Override
    public boolean register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setId(0);
//        System.out.println(user.getPassword());
        return userMapper.insert(user)>0;
    }

    @Override
    public boolean resetPassword(Integer id,String oldPassword,String newPassword) {
        User user=userMapper.selectOne(new QueryWrapper<User>().eq("id",id));
        if (user==null){
            return false;
        }
        if(passwordEncoder.matches(oldPassword,user.getPassword())){
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return userMapper.updateById(user)>0;
    }

    @Override
    public boolean ModifyPersonalData(Integer id, boolean gender, Date birthday) {
        User user = userMapper.selectById(id);
        if (user==null){
            return false;
        }
        user.setGender(gender);
        user.setBirthday(birthday);
        return userMapper.updateById(user)>0;
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("id",id));
    }

    @Override
    public User getUserByName(String name) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("name",name));
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("email",email));
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("phone",phone));
    }

    @Override
    public List<User> getAllUser() {
        return userMapper.selectList(null);
    }

    @Override
    public boolean update(User user) {
        return userMapper.updateById(user)>0;
    }
    @Override
    public void deleteUser(int id) {
        userMapper.deleteById(id);
    }
}
