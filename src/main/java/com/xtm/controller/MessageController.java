package com.xtm.controller;

import com.xtm.model.Admin;
import com.xtm.model.Message;
import com.xtm.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:藏剑
 * @date:2019/6/18 10:26
 */
@Slf4j
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

   /* @PostMapping("/message")
    public String saveMessage(Message message){
        message.setCreateTime(new Date());
        System.out.println(message);
       messageService.saveMessage(message);
        return "redirect:/listMessage.html";
    }*/

    @DeleteMapping("/message")
    @ResponseBody
    public String deleteMessage(String ids) {
        log.info("要删除的留言id:" + ids);
        String[] sids = ids.split(",");
        messageService.deleteMessages(sids);
        return "{\"msg\": \"删除成功\"}";
    }

    @GetMapping("/message/{id}")
    public String messageUpdate(@PathVariable("id") Integer id, Model model) {
        Message message = messageService.getMessageById(id);
        log.info(message.toString());
        model.addAttribute("message", message);
        return "message/modifyMessage";
    }


    @ResponseBody
    @GetMapping("/messages")
    public Map<String, Object> getMessages(HttpServletRequest request, @RequestParam(value = "content", required = false) String content) {
        //从视图层获取每一页显示的用户数量
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        //从视图层获取当前第几页
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        //如果没有搜索栏没有内容
        if (content == "" || content == null) {
            //查找所有留言
            List<Message> messages = messageService.getAllMessages(admin.getId());
            int count = messages.size();
            result.put("count", count);
            result.put("data", messages);
        }
        //如果搜索栏有内容
        else {
            //查找相关留言
            Page<Message> messages = messageService.findByContent(content, pageNumber, pageSize, admin.getId());
            result.put("count", messages.getTotalElements());
            /*messages.getContent();
            JSONArray json = JSONArray.fromObject(messages.getContent());*/
            result.put("data", messages.getContent());
        }
        //返回layui数据表格需要的json数据
        result.put("code", 0);
        result.put("msg", "");
        log.info(result.toString());
        return result;

    }

    /*@PutMapping("/message")
    @ResponseBody
    public String updateMessage(Message message){
        messageService.updateMessage(message);
        return "success";
    }*/

    @PostMapping("/message/read")
    @ResponseBody
    public String readMessages(String ids) {
        log.info("要已读的留言id:" + ids);
        String[] sids = ids.split(",");
        messageService.readMessages(sids);
        return "success";
    }
}
