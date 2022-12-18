package xyz.charlespro.coder.service;

import xyz.charlespro.coder.pojo.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String, Object> login(String email, String password);
    boolean register(User user);
    boolean resetPassword(Integer id,String oldPassword,String newPassword);
    boolean ModifyPersonalData(Integer id, boolean gender, Date birthday);
    User getUserById(int id);
    User getUserByName(String name);
    User getUserByEmail(String email);
    User getUserByPhone(String phone);
    List<User> getAllUser();
    boolean update(User user);
    void deleteUser(int id);
}
