package com.calvin.bookstorecontroller.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.calvin.bookstoreuser.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegisterController {

    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}",
            url = "dubbo://localhost:${dubbo.protocol.port.user}")
    private UserService userService;

    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public String registerChange() {
        return "register/register.html";
    }

    @RequestMapping(value = "/register/confirm", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String registerConfirm(@RequestParam String username, @RequestParam String password, @RequestParam String
            gender, @RequestParam String email, @RequestParam String address, @RequestParam String telephone) {
        if (username.equals("") || password.equals("") || gender.equals("") || email.equals("") || telephone.equals("")
                || address.equals(""))
            return "false";
        else {
            boolean isMan = gender.equals("man");
            if (userService.addUser(username, password, isMan, email, telephone, address))
                return "true";
            else
                return "false";
        }
    }


}
