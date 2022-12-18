package xyz.charlespro.coder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import xyz.charlespro.coder.service.QueueMessageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sendMessage")
public class SendAndReceiveMessageController {
    @Autowired
    private QueueMessageService service;
    @ResponseBody
    @RequestMapping("/send")
    public Map<String,Object> send(@RequestParam String sendTo,String content){
        Map<String,Object> resMap = new HashMap<String, Object>();
        Boolean success = service.sendMessage(sendTo,content);
        resMap.put("success",success);
        return resMap;
    }
}
