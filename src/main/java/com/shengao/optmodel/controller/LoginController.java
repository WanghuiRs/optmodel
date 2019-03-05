package com.shengao.optmodel.controller;


import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.shengao.optmodel.controller.FileUtil.writeToFile;

@Controller
public class LoginController {


    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "/userLogin",method= RequestMethod.POST)
    public String verifyLogin(HttpServletRequest request, HttpSession session) throws Exception{
        String name = request.getParameter("name");
        String psd = request.getParameter("psd");
        String userName = "admin";
        String password = "admin";
        System.out.println(name +" -- " + psd);
        if (userName.equals(name) && password.equals(psd)) {
            session.setAttribute("username",userName);
            return "redirect:/home";
        } else {
            return "redirect:/login";
        }
    }
}
