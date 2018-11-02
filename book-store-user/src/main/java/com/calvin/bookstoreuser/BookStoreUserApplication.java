package com.calvin.bookstoreuser;


import com.calvin.bookstoreuser.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BookStoreUserApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext applicationContext = SpringApplication.run(BookStoreUserApplication
                .class, args);
        UserService userService =  applicationContext.getBean(UserService.class);
//
//        User user = new User("lisa", "123456", User.WOMAN, "liys233@mail2.sysu.edu.cn",
//                new ArrayList<>(Arrays.asList("广东省广州市华南理工大学C9宿舍")), "15603002209");
//        userService.addUser(user);
//        user = new User("calvin", "123456", User.MAN, "chenjj233@mail2.sysu.edu.cn",
//                new ArrayList<>(Arrays.asList("广东省广州市中山大学工学院")), "15603002045");
//        userService.addUser(user);
        System.out.println(userService.findUserByUsername("calvin"));
    }
}
