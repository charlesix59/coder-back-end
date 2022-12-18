package xyz.charlespro.coder.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;

@WebFilter(urlPatterns = {"/admin/login","/hr/login","/user/login","/user/register","/hr/register"})
public class NoRepeatSubmitFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "accept,content-type");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
        String email = httpServletRequest.getParameter("email");
        if (email.isEmpty()){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        HttpSession session = httpServletRequest.getSession();
        Calendar calendar = Calendar.getInstance();
        int nowSecond = calendar.get(Calendar.SECOND);
        System.out.println(email);
        Object o = session.getAttribute(httpServletRequest.getRequestURI()+email);
        System.out.println(o);
        o=o==null?nowSecond-6:o;
        int second = (int)o;
        System.out.println("second:"+second);
        if(!(session.getAttribute(httpServletRequest.getRequestURI()+email)==null)){
            System.out.println("nowSecond:"+nowSecond);
            if (Math.abs(second-nowSecond)>=55||Math.abs(second-nowSecond)<=5){
                httpServletResponse.sendError(403,"重复提交，请求失败");
            }
        }
        session.setAttribute(httpServletRequest.getRequestURI()+email,nowSecond);
        servletResponse.setContentType("text/html;charset=UTF-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
