package xyz.charlespro.coder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import xyz.charlespro.coder.service.QueueMessageService;
@Service
public class QueueMessageServiceImpl implements QueueMessageService {
    private int count = 0;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Override
    public boolean sendMessage(String sentTo,String content) {
        try {
            jmsTemplate.convertAndSend(sentTo,content);
        }
        catch (JmsException e){
            return false;
        }

        return true;
    }
    @JmsListener(destination = "queue")
    @Override
    public void receiveMessage(String content){
        System.out.println(content);
    }
}
