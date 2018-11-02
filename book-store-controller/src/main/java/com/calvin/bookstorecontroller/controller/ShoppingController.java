package com.calvin.bookstorecontroller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.calvin.bookstoreuser.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ShoppingController {
    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:${dubbo.protocol.port.user}")
    private UserService userService;

    @RequestMapping(value = {"/", "/shopping"}, method = {RequestMethod.GET, RequestMethod.POST})
    public void shopping(HttpServletRequest request, HttpServletResponse response) throws
            IOException, ServletException {
        request.getRequestDispatcher("/cart/home.html").forward(request, response);
    }

    @RequestMapping(value = "/shopping/getUsername", method = RequestMethod.POST)
    @ResponseBody
    public String getUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("username") != null)
            return (String) httpSession.getAttribute("username");
        else
            return null;
    }

}
