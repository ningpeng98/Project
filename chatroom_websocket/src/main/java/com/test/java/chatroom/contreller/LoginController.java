package com.test.java.chatroom.contreller;
/**
 * 前后端交互：
 * 用户登录
 * */

import com.test.java.chatroom.config.FreeMarkerListener;
import com.test.java.chatroom.service.AccountService;
import com.test.java.chatroom.utils.CommUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=utf8");
        PrintWriter out = resp.getWriter();
        if (CommUtils.strIsNull(userName)||CommUtils.strIsNull(password)){
            //输入为空，登陆失败，继续留在登录页面
            out.println("<script>\n" +
                    "    alert(\"用户名或密码为空！\");\n" +
                    "    window.location.href = \"/index.html\";\n" +
                    "</script>");
        }
        if(accountService.userLogin(userName,password)){
            //登陆成功，跳转到聊天页面
            //加载chat.ftl
            Template template = getTemplate(req,"/chat.ftl");
            Map<String, String> map = new HashMap<>();
            map.put("username",userName);
            try {
                template.process(map,out);
            } catch (TemplateException e) {
                e.printStackTrace();
            }

        }else{
            //登录失败，留在登录界面
            out.println("<script>\n" +
                    "    alert(\"用户名或密码不正确！\");\n" +
                    "    window.location.href = \"/index.html\";\n" +
                    "</script>");

        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    private Template getTemplate(HttpServletRequest req,String fileName) {
        Configuration cfg = (Configuration)
                req.getServletContext().getAttribute(FreeMarkerListener.TEMPLATE_KEY);
        try {
            return cfg.getTemplate(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
