package com.example.test.controller;

import com.example.test.entity.TestUser;
import com.example.test.service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
@Controller
public class TestUserController {

    @Resource
    UserInfoService userInfoService;


//    @RequestMapping("/")
//    public String index() {
//        return "redirect:/list";
//    }

   // @RequestMapping(value = "/list",method = "GET")
    @RequestMapping("/list")
    @RequiresPermissions("userInfo:view")//权限管理;

    public String list(Model model) {
        List<TestUser> users=userInfoService.getUserList();
        model.addAttribute("users", users);
        System.out.println("------"+users.get(0));
        return "user/list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "user/userAdd";
    }

    @RequestMapping("/add")
    public String add(TestUser user) {
        userInfoService.save(user);
        return "redirect:/list";
    }

    @RequestMapping("/toEdit")
    public String toEdit(Model model,Long id) {
        TestUser user=userInfoService.findUserById(id);
        model.addAttribute("user", user);
        return "user/userEdit";
    }

    @RequestMapping("/edit")
    public String edit(TestUser user) {
        userInfoService.edit(user);
        return "redirect:/list";
    }


    @RequestMapping("/delete")
    public String delete(Long id) {
        userInfoService.delete(id);
        return "redirect:/list";
    }
}
