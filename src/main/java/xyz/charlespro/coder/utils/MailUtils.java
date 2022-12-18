package xyz.charlespro.coder.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class MailUtils {
    @Resource
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private ServletContext servletContext;
    /**
     * 此方法用于获取时间之差
     * @param endDate ed
     * @param nowDate nd
     * @return gaps
     */
    private static long getDatePoor(LocalDateTime endDate, LocalDateTime nowDate) {
        Duration duration = Duration.between(nowDate, endDate);
        return Math.abs(duration.toMinutes());
    }

    /**
     * 此方法用于发送提醒邮件
     * @param sendTo 收件人邮箱
     * @param title 主题
     * @param text 内容
     */
    public void sendMail(String sendTo,String title,String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("3240259873@qq.com");
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 此方法用于向邮箱发送验证码并把验证码存在session上
     * @param sendTo 邮箱
     */
    public boolean sendVerificationCode(String sendTo){
        Random random = new Random();
        StringBuilder v12 = new StringBuilder();
        for (int i=0;i<6;i++)
        {
            v12.append(random.nextInt(10));
        }
        sendMail(sendTo,"验证码","您正在进行邮箱验证，验证码为："+v12+"，有效时长2分钟。");
        servletContext.setAttribute("vCode"+sendTo, v12.toString());
        LocalDateTime date = LocalDateTime.now();
        servletContext.setAttribute("vCodeTime"+sendTo,date);
        return true;
    }

    /**
     * 此方法用于验证验证码是否正确
     * @param sendTo 邮箱
     * @param verificationCode 用户输入的验证码
     * @return 0表示超时，-1表示验证码错误，1表示正确
     */
    public int verification(String sendTo,String verificationCode){
        LocalDateTime date = (LocalDateTime) servletContext.getAttribute("vCodeTime"+sendTo);
        LocalDateTime nowDate = LocalDateTime.now();
        System.out.println(date);
        System.out.println(nowDate);
        if (getDatePoor(date,nowDate)>2)
            return 0;
        String vCode = (String) servletContext.getAttribute("vCode"+sendTo);
        if(vCode.equals(verificationCode))
            return 1;
        return -1;
    }

}
