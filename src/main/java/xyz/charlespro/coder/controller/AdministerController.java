package xyz.charlespro.coder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.charlespro.coder.pojo.*;
import xyz.charlespro.coder.service.AdministerService;
import xyz.charlespro.coder.service.HrService;
import xyz.charlespro.coder.service.JobService;
import xyz.charlespro.coder.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdministerController {
    @Autowired
    private AdministerService administerService;
    @Autowired
    private HrService hrService;
    @Autowired
    private UserService userService;

    /**
     * 此方法处理登录请求
     * @param administer
     * @return success：删除是否成功
     */
    @ResponseBody
    @RequestMapping("/login")
    public Map<String,Object> login(@RequestBody Administer administer){
        Map<String,Object> resMap= new HashMap<>();
        boolean res = administerService.login(administer.getId(), administer.getPassword());
        resMap.put("success",res);
        return resMap;
    }

    /**
     * 删除一个应聘者
     * **/
    @PostMapping("/delete/user")
    @ResponseBody
    public void deleteUser(String id) {
        int idd = Integer.parseInt(id);
        userService.deleteUser(idd);
    }

    /**
     * 删除一个招聘者
     * **/
    @PostMapping("/delete/hr")
    @ResponseBody
    public void deleteHr(String id) {
        int idd = Integer.parseInt(id);
        hrService.deleteHr(idd);
    }

    /**
     * 该方法用于获取所有的招聘者
     * **/
    @GetMapping("/findHrs")
    public String findHrs(Model model) {
        model.addAttribute(
                "hrList", hrService.getAllUser()
        );
        return "administrator_hr";
    }

    /**
     * 该方法用于获取所有的应聘者
     * **/
    @RequestMapping("/findUsers")
    public String findUsers(Model model) {
        model.addAttribute("userList", userService.getAllUser());
        return "administrator_user";
    }


    /**
     * 该方法用于修改单个招聘者（公司）信息
     **/
    @ResponseBody
    @RequestMapping("/modify/hr")
    public void updateHr(Hr hr) {
        String id= String.valueOf(hr.getId());
        int idd = Integer.parseInt(id);
        System.out.println(id);
        String name = String.valueOf(hr.getName());
        String email = String.valueOf(hr.getEmail());
        String phone = String.valueOf(hr.getPhone());
        //int phonee = Integer.parseInt(phone);
        String password = String.valueOf(hr.getPassword());
        //String birthday = String.valueOf(hr.getBirthday());
        /**
         *avator
         *gender
         * company_id
         * **/
        Hr hrr = new Hr(idd,name,email,phone,password,null,null,null,null);
        hrService.update(hrr);
    }


    /**
     * 该方法用于修改单个应聘者信息
     **/
    @RequestMapping("/modify/user")
    @ResponseBody
    public void updateUser(User user) {

        String id= String.valueOf(user.getId());
        System.out.println(id);
        int idd = Integer.parseInt(id);
        System.out.println(idd);
        String name = String.valueOf(user.getName());
        String email = String.valueOf(user.getEmail());
        String phone = String.valueOf(user.getPhone());
        //int phonee = Integer.parseInt(phone);
        String password = String.valueOf(user.getPassword());

        User userr = new User(idd,name,email,phone,password,null,true,null);

        userService.update(userr);
    }

    /**
     * 该方法用于查询单个应聘者信息
     **/
    @ResponseBody
    @GetMapping("/searchHr")
    public Hr searchHr( String id ) {
        int idd = Integer.parseInt(id);
        Hr hr =  hrService.getHrById(idd);
        return hr;
    }

    /**
     * 该方法用于查询单个应聘者信息
     **/
    @ResponseBody
    @RequestMapping("/searchUser")
    public User searchUser(String id) {
        int idd = Integer.parseInt(id);
        User user = userService.getUserById(idd);
        return user;
    }


}
