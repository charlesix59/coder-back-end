package xyz.charlespro.coder.service;

import xyz.charlespro.coder.pojo.Hr;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface HrService {
    Map<String, Object> login(String email, String password);
    boolean register(Hr hr);
    boolean ModifyPersonalData(Integer id, boolean gender, Date birthday);
    Hr getHrById(int id);
    Hr getHrByName(String name);
    Hr getHrByEmail(String email);
    Hr getHrByPhone(String phone);
    List<Hr> getAllUser();
    Boolean resetPassword(Integer id, String oldPassword, String newPassword);
    boolean update(Hr hr);
    void deleteHr(int id);
}
