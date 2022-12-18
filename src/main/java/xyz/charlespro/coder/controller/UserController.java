package xyz.charlespro.coder.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.charlespro.coder.pojo.Hr;
import xyz.charlespro.coder.pojo.User;
import xyz.charlespro.coder.service.HrService;
import xyz.charlespro.coder.service.JobService;
import xyz.charlespro.coder.service.UserService;
import xyz.charlespro.coder.utils.JwtUtils;
import xyz.charlespro.coder.utils.MailUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JobService jobService;
    @Autowired
    HrService hrService;
    @Resource
    MailUtils mailUtils;
    /**
     * 此方法仅用于测试
     * @return test fn
     */
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "hello world!";
    }

    /**
     * 此方法用于处理应聘者登录请求
     * @param user 应聘者（只有账号和密码）
     * @return success：请求是否成功；token：生成token便于校验用户登录信息
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody User user){
        return userService.login(user.getEmail(),user.getPassword());
    }

    /**
     * 此方法用于处理应聘者注册请求
     * @param user 新建的user对象
     * @return success：注册请求是否成功
     */
    @RequestMapping("/register")
    public Map<String,Object> register(@RequestBody User user){
        boolean res = userService.register(user);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于修改密码
     * @param userInfo 用户信息
     * @return success：用户是否注册成功
     */
    @ResponseBody
    @RequestMapping("/modify/password")
    public Map<String, Object> resetPassword(@RequestBody Map<String,Object> userInfo){
        Map<String,Object> resMap = new HashMap<>();
        Claims claims = JwtUtils.verifyJwt((String) userInfo.get("id"));
        Integer id= (Integer) claims.get("userId");
        Boolean res=userService.resetPassword(id, (String) userInfo.get("oldPassword"), (String) userInfo.get("newPassword"));
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于处理应聘者账户按邮箱修改密码的请求（前半部分）
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/sendVerificationCode")
    public Map<String, Object> sendVerificationCode(@RequestBody User user){
        String email = user.getEmail();
        boolean success = mailUtils.sendVerificationCode(email);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("success",success);
        return resMap;
    }

    /**
     * 此方法用于获取用户输入验证码验证
     * @param userInfo 邮箱+新密码+code
     * @return success：是否成功，若否，则info包含验证码错误信息或超时信息
     */
    @ResponseBody
    @RequestMapping("/verification")
    public Map<String, Object> verification(@RequestBody Map<String,Object> userInfo){
        String email = (String) userInfo.get("email");
        String code = (String) userInfo.get("code");
        int state = mailUtils.verification(email,code);
        Map<String,Object> resMap = new HashMap<>();
        System.out.println(state);
        if (state!=1)
            resMap.put("info",state==-1?"验证码错误":"超时");
        else {
            int id = (Integer)userInfo.get("id");
//            Integer id = (Integer) JwtUtils.verifyJwt((String) userInfo.get("id")).get("userId");
            String password = (String) userInfo.get("password");
            String oldPassword = userService.getUserById(id).getPassword();
            boolean res = userService.resetPassword(id,oldPassword,password);
            resMap.put("success",res);
        }
        return resMap;
    }
    /**
     *此方法用于修改用户个人信息
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/modify/data")
    public Map<String, Object> modifyPersonalData(@RequestBody Map<String,Object> userInfo) throws ParseException {
        Map<String,Object> resMap = new HashMap<>();
        Integer id = (Integer) JwtUtils.verifyJwt((String) userInfo.get("id")).get("userId");
        Boolean gender = (Boolean) userInfo.get("gender");
        Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse((String) userInfo.get("birthday"));
        boolean res = userService.ModifyPersonalData(id,gender,birthday);
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于处理上传简历请求
     * @return success：上传是否成功
     */
    @ResponseBody
    @RequestMapping("/upload/profile")
    public String uploadProfileFile(){
        return null;
    }

    /**
     * 此方法用于处理用户上传简历的请求
     * @return success：上传是否成功
     */
    @ResponseBody
    @RequestMapping("/add/profile")
    public String addProfile(){
        return null;
    }


    /**
     * 此方法用于处理用户修改简历的请求
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/modify/profile")
    public String modifyProfile(){
        return null;
    }

    /**
     * 此方法用于处理申请工作请求
     * @return success：申请请求是否成功
     */
    @ResponseBody
    @RequestMapping("/apply")
    public Map<String, Object> applyJob(@RequestBody Map<String,Object> userInfo){
        Map<String,Object> resMap = new HashMap<>();
        Integer userId = (Integer) JwtUtils.verifyJwt(String.valueOf(userInfo.get("userId"))).get("userId");
        boolean res = jobService.applyJob(userId, Integer.parseInt((String) userInfo.get("jobId")));
        resMap.put("success",res);
        Hr hr = hrService.getHrById(jobService.getJobById(Integer.parseInt((String) userInfo.get("jobId"))).getCreatorId());
        System.out.println(hr);
        try {
            mailUtils.sendMail(hr.getEmail(),"Notice of application","You have an application to process");
        } catch (Exception e){
            System.out.println(e);
        }
        return resMap;
    }

    /**
     * 此方法用于处理取消申请工作请求
     * @return success：取消申请请求是否成功
     */
    @ResponseBody
    @RequestMapping("/cancel")
    public Map<String, Object> cancelApply(@RequestBody Map<String,Object> userInfo){
        Map<String,Object> resMap = new HashMap<>();
        Integer userId = (Integer) JwtUtils.verifyJwt((String) userInfo.get("userId")).get("userId");
        boolean res = jobService.cancelApplication(userId, (Integer) userInfo.get("jobId"));
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于获取job申请列表
     * @return  success：获取是否成功；data：job申请列表
     */
    @ResponseBody
    @RequestMapping("/get/apply")
    public Map<String, Object> getApplyList(@RequestBody Map<String,Object> userInfo){
        Map<String,Object> resMap = new HashMap<>();
        Integer id = (Integer) JwtUtils.verifyJwt((String) userInfo.get("id")).get("userId");
        List<Map<String, Object>> allApplication = jobService.getAllApplication(id);
        resMap.put("success", true);
        resMap.put("data",allApplication);
        return resMap;
    }
}
