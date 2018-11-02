package com.calvin.bookstorecontroller.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.calvin.bookstorebasis.entity.BuyerCart;
import com.calvin.bookstoreuser.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:${dubbo.protocol.port.user}")
    private UserService userService;

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            response.sendRedirect("/shopping");
        } else
            response.sendRedirect("/login/login.html");
    }

    @RequestMapping(value = "/login/verity", method = RequestMethod.POST)
    @ResponseBody
    public String verity(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        if (userService.verityPassword(username, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            request.changeSessionId();
            return "true";
        } else
            return "false";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession httpSession = request.getSession();
        httpSession.invalidate();
        BuyerCartController.destroyCookie(response);
        response.sendRedirect("/");
    }


}
