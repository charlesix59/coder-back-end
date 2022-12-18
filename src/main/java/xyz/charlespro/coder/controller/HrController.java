package xyz.charlespro.coder.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.charlespro.coder.pojo.*;
import xyz.charlespro.coder.service.HrService;
import xyz.charlespro.coder.service.JobService;
import xyz.charlespro.coder.service.UserService;
import xyz.charlespro.coder.utils.JwtUtils;
import xyz.charlespro.coder.utils.MailUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hr")
public class HrController {
    @Autowired
    private HrService hrService;
    @Autowired
    private JobService jobService;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private UserService userService;

    /**
     * 此方法用于处理招聘者登录请求
     * @param hr 招聘者（只有账号密码）
     * @return success：请求是否成功；token：生成token便于校验用户登录信息
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody Hr hr){
        return hrService.login(hr.getEmail(), hr.getPassword());
    }

    /**
     * 此方法用于处理招聘者注册请求
     * @param hr 一个新建的hr对象
     * @return success：注册请求是否成功
     */
    @RequestMapping("/register")
    public Map<String, Object> register(@RequestBody Hr hr){
        boolean res = hrService.register(hr);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于修改密码
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/modify/password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, Object> hrInfo){
        Map<String,Object> resMap = new HashMap<>();
        Claims claims = JwtUtils.verifyJwt((String) hrInfo.get("id"));
        Integer id= (Integer) claims.get("userId");
        Boolean res=hrService.resetPassword(id, (String) hrInfo.get("oldPassword"), (String) hrInfo.get("newPassword"));
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于处理应聘者账户按邮箱修改密码的请求（前半部分）
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/sendVerificationCode")
    public Map<String, Object> sendVerificationCode(@RequestBody Hr hr){
        String email = hr.getEmail();
        boolean success = mailUtils.sendVerificationCode(email);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("success",success);
        return resMap;
    }

    /**
     * 此方法用于获取用户输入验证码验证
     * @param hrInfo 邮箱+新密码+code
     * @return success：是否成功，若否，则info包含验证码错误信息或超时信息
     */
    @ResponseBody
    @RequestMapping("/verification")
    public Map<String, Object> verification(@RequestBody Map<String,Object> hrInfo){
        String email = (String) hrInfo.get("email");
        String code = (String) hrInfo.get("code");
        int state = mailUtils.verification(email,code);
        Map<String,Object> resMap = new HashMap<>();
        System.out.println(state);
        if (state!=1)
            resMap.put("info",state==-1?"验证码错误":"超时");
        else {
            int id = (Integer)hrInfo.get("id");
//            Integer id = (Integer) JwtUtils.verifyJwt((String) userInfo.get("id")).get("userId");
            String password = (String) hrInfo.get("password");
            String oldPassword = hrService.getHrById(id).getPassword();
            boolean res = hrService.resetPassword(id,oldPassword,password);
            resMap.put("success",res);
        }
        return resMap;
    }

    /**
     * 此方法用于处理招聘者用户修改个人信息的请求
     * @param hrInfo 招聘者用户账号
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/modify/data")
    public Map<String, Object> modifyPersonalData(@RequestBody Map<String,Object> hrInfo) throws ParseException {
        Map<String,Object> resMap = new HashMap<>();
        Integer id = (Integer) JwtUtils.verifyJwt((String) hrInfo.get("id")).get("userId");
        Boolean gender = (Boolean) hrInfo.get("gender");
        Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse((String) hrInfo.get("birthday"));
        boolean res = hrService.ModifyPersonalData(id,gender,birthday);
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于处理退出登录的请求
     * @return success：退出是否成功
     */
    @RequestMapping("/quit")
    public Map<String, Object> quitLogin()
    {
        return null;
    }

    /**
     * 此方法用于处理招聘者新建job对象的请求
     * @param jobInfo 新建job对象
     * @return success：新建是否成功
     */
    @ResponseBody
    @RequestMapping("/add/job")
    public Map<String, Object> addJob(@RequestBody Map<String,Object> jobInfo){
        System.out.println(jobInfo);
        Integer hrId = (Integer) JwtUtils.verifyJwt((String) jobInfo.get("token")).get("userId");
        Job job = new Job();
        Company company = new Company();
        Hr hr = hrService.getHrById(hrId);
        Integer companyId = hr.getCompanyId();
        company.setId(companyId);

        job.setId(0);
        job.setCompany(company);
        job.setCreatorId(hrId);
        job.setDescription((String) jobInfo.get("description"));
        job.setEducationRequirement((String) jobInfo.get("educationRequirement"));
        job.setExperienceRequirement((String) jobInfo.get("experienceRequirement"));
        job.setFirstCategories((String) jobInfo.get("firstCategory"));
        job.setName((String) jobInfo.get("name"));
        job.setRecruitNumber((Integer.parseInt((String) jobInfo.get("recruitNumber")))+1);
        job.setSecondCategories((String) jobInfo.get("secondCategory"));
        job.setTag("无");

        boolean res=jobService.addNewJob(job);
        Map<String,Object> resMap= new HashMap<>();
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 此方法用于处理招聘者修改job对象的请求
     * @param job 欲修改的job对象
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/modify/job")
    public Map<String, Object> modifyJob(@RequestBody Job job){
        boolean res = jobService.modifyJob(job);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("success",res);
        return  resMap;
    }

    /**
     * 此方法用于修改应聘者userJob的状态
     * @param userJob 应聘者job对象
     * @return success：修改是否成功
     */
    @ResponseBody
    @RequestMapping("/modify/application")
    public Map<String, Object> acceptApplication(@RequestBody UserJob userJob){
        System.out.println(userJob);
        boolean res = jobService.setJobState(userJob);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("success",res);
        User user = userService.getUserById(userJob.getUserId());
        mailUtils.sendMail(user.getEmail(),"申请通知",(user.isGender()?"Mr ":"Miss")+user.getName()+",Your application has "+(userJob.getState().equals("pass")?"passed":"been failed"));
        return resMap;
    }

    /**
     * 此方法用于获取所有申请列表
     * @param tokenInfo 校验
     * @return success：是否成功；data：申请列表
     */
    @ResponseBody
    @RequestMapping("/get/application")
    public Map<String,Object> getAllApplications(@RequestBody Map<String,Object> tokenInfo){
        Integer id = (Integer) JwtUtils.verifyJwt((String) tokenInfo.get("id")).get("userId");
        List<Map<String, Object>> applications = jobService.getALlApplicationsSubmitted(id);
        Map<String,Object> res=new HashMap<>();
        res.put("success",applications==null);
        res.put("data",applications);
        return res;
    }

/*
    @ResponseBody
    @RequestMapping("/file")
    public ResponseEntity<FileSystemResource> downloadFile(@RequestParam int id) {
    ProfileService profileService = new ProfileServiceImpl();
    String path = profileService.getProfileById(id).getFileUrl();
    File file = new File(path);
    if (file == null) {
        return null;
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
    headers.add("Content-Disposition", "attachment; filename=" + file.getName());
    headers.add("Pragma", "no-cache");
    headers.add("Expires", "0");
    headers.add("Last-Modified", new Date().toString());
    headers.add("ETag", String.valueOf(System.currentTimeMillis()));
    return ResponseEntity
            .ok()
            .headers(headers)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(new FileSystemResource(file));
    }
*/
}
