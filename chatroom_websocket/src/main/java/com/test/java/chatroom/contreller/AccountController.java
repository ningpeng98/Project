package com.test.java.chatroom.contreller;
/**
 * 前后端交互：
 * 用户注册
 * */
import com.test.java.chatroom.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/doRegister")
public class AccountController extends HttpServlet {
    private AccountService accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        resp.setContentType("text/html;charset=utf8");
        PrintWriter writer = resp.getWriter();
        if(accountService.userRegister(userName,password)){
            //用户注册成功，弹框提示，返回登录页面
            writer.println("<script>\n" +
                    "    alert(\"注册成功\");\n" +
                    "    window.location.href=\"/index.html\";\n" +
                    "</script>");

        }else{
            //注册失败，保留原页面
            writer.println("<script>\n" +
                    "    alert(\"注册失败\");\n" +
                    "    window.location.href=\"/registration.html\";\n" +
                    "</script>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
