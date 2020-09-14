package com.xtm.controller;

import com.xtm.model.Admin;
import com.xtm.service.AdminService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author:藏剑
 * @date:2019/6/18 10:26
 */
@Controller
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/login")
    public String login(String account, String password, HttpServletRequest httpServletRequest, Map<String, Object> map) {
        Admin admin = adminService.getAdmin(account, password);
        HttpSession session = httpServletRequest.getSession();
        String rightCode = (String) session.getAttribute("rightCode");
        String tryCode = httpServletRequest.getParameter("tryCode");
        if (admin != null && rightCode.equals(tryCode)) {
            session.setAttribute("admin", admin);          //用户信息保存到session
            //获取头像
            String headUrl = admin.getHeadUrl();
            session.setAttribute("headUrl", headUrl);
            return "redirect:/dashboard.html";
        } else if (!rightCode.equals(tryCode)) {
            map.put("msg", "验证码错误");
            return "login";
        } else {
            map.put("msg", "用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping("/admin/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/";
    }

    @PutMapping("/admin")
    @ResponseBody
    public String updateAdmin(Admin admin, HttpSession session) {
        System.out.println(admin);
        //获取旧的admin
        Admin oldAdmin = (Admin) session.getAttribute("admin");
        session.removeAttribute("admin");
        session.setAttribute("admin", admin);
        adminService.updateAdmin(oldAdmin, admin);
        return "success";
    }

    @ResponseBody
    @PostMapping("/admin/upload_head")
    public Object uploadImg(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        Map<String, String> map = new HashMap<>();
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        HttpSession session = request.getSession();
        final String baseUrl = "asserts/images/";
        String headUrl = "";
        if (!uploadFile.isEmpty()) {
            //头像保存路径
            String filepath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/" + baseUrl;
            File file = new File(filepath);
            //获取头像名称
            String fileName = uploadFile.getOriginalFilename();
            //设置uuid
            String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            fileName = uuid + fileName;
            log.info("fileName:" + fileName);
            log.info("filepath:" + filepath);
            headUrl = baseUrl + fileName;
            //存储文件
            //上传到本地
            uploadFile.transferTo(new File(file, fileName));
            if (admin != null)//若已有头像
            {
                //覆盖url
                admin.setHeadUrl(headUrl);
                //替换头像session
                session.setAttribute("headUrl", headUrl);
            }
            //数据库更新记录
            adminService.updateAdminHead(admin);
            map.put("msg", "上传成功");
        }
        return map;
    }
}
