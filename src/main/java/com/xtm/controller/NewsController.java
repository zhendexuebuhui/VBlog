package com.xtm.controller;

import com.xtm.model.*;
import com.xtm.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author:藏剑
 * @date:2019/6/18 10:26
 */
@Slf4j
@Controller
@Api(tags = "新闻")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/news")
    public String saveNews(News news) {
        news.setCreateTime(new Date());
        newsService.saveNews(news);
        return "redirect:/listNews.html";
    }

    @DeleteMapping("/news")
    @ResponseBody
    public String deleteNews(String ids) {
        log.info("要删除的文章id:" + ids);
        String[] sids = ids.split(",");
        newsService.deleteNewss(sids);
        return "{\"msg\": \"删除成功\"}";
    }


    @GetMapping("/news/{id}")
    public String newsUpdate(@PathVariable("id") Integer id, Model model) {
        News news = newsService.getNewsById(id);
        log.info(news.toString());
        model.addAttribute("news", news);
        return "news/modifyNews";
    }

    @ApiOperation(value = "获取所有新闻")
    @ResponseBody
    @GetMapping("/front/news")
    public List<NewsAuthor> getArticles() {
        List<Object> articles = newsService.getAllNewss();
        List<NewsAuthor> views = new ArrayList<NewsAuthor>();
        for (Object o : articles) {
            Object[] rowArray = (Object[]) o;
            NewsAuthor view = new NewsAuthor();
            view.setId((Integer) rowArray[0]);
            view.setAuthor((String) rowArray[1]);
            view.setContent((String) rowArray[2]);
            view.setCreateTime((Date) rowArray[3]);
            view.setTitle((String) rowArray[4]);
            view.setAccount((String) rowArray[5]);
            view.setClick((Integer) rowArray[6]);
            views.add(view);
        }
        return views;
    }

    @ApiOperation(value = "根据id获取某篇新闻")
    @ResponseBody
    @GetMapping("/front/news/{id}")
    public NewsAuthor getArticleById(@PathVariable("id") Integer id) {
        NewsAuthor newsAuthor = newsService.getNewsAuthorById(id);
        return newsAuthor;
    }

    @ResponseBody
    @GetMapping("/news")
    public Map<String, Object> getNews(HttpServletRequest request, @RequestParam(value = "content", required = false) String content) {
        //从视图层获取每一页显示的用户数量
        int pageSize = Integer.parseInt(request.getParameter("limit"));
        //从视图层获取当前第几页
        int pageNumber = Integer.parseInt(request.getParameter("page"));
        Map<String, Object> result = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        //如果没有搜索栏没有内容
        if (content == "" || content == null) {
            //查找所有文章
            List<Object> newss = newsService.getAllNewss();
            List<NewsAuthor> views = new ArrayList<NewsAuthor>();
            for (Object o : newss) {
                Object[] rowArray = (Object[]) o;
                NewsAuthor view = new NewsAuthor();
                view.setId((Integer) rowArray[0]);
                view.setAuthor((String) rowArray[1]);
                view.setContent((String) rowArray[2]);
                view.setCreateTime((Date) rowArray[3]);
                view.setTitle((String) rowArray[4]);
                view.setAccount((String) rowArray[5]);
                view.setClick((Integer) rowArray[6]);
                views.add(view);
            }
            result.put("count", views.size());
            result.put("data", views);
        }
        //如果搜索栏有内容
        else {
            //查找相关文章
            Page<Object> newss = newsService.findByContent(content, pageNumber, pageSize);
            List<NewsAuthor> views = new ArrayList<NewsAuthor>();
            for (Object o : newss) {
                Object[] rowArray = (Object[]) o;
                NewsAuthor view = new NewsAuthor();
                view.setId((Integer) rowArray[0]);
                view.setAuthor((String) rowArray[1]);
                view.setContent((String) rowArray[2]);
                view.setCreateTime((Date) rowArray[3]);
                view.setTitle((String) rowArray[4]);
                view.setAccount((String) rowArray[5]);
                view.setClick((Integer) rowArray[6]);
                views.add(view);
            }
            result.put("count", views.size());
//            JSONArray json = JSONArray.fromObject(newss.getContent());
            result.put("data", views);
        }
        //返回layui数据表格需要的json数据
        result.put("code", 0);
        result.put("msg", "");
        log.info(result.toString());
        return result;

    }

    @PutMapping("/news")
    @ResponseBody
    public String updateNews(News news) {
        newsService.updateNews(news);
        return "success";
    }

    @GetMapping("/sortNewsClick")
    @ResponseBody
    public List<AdminClick> getAndSortClick() {
        List<Object> userAndClicks = newsService.getAndSortClick();
        List<AdminClick> views = new ArrayList<AdminClick>();
        for (Object o : userAndClicks) {
            Object[] rowArray = (Object[]) o;
            AdminClick view = new AdminClick();
            view.setAuthor((String) rowArray[0]);
            view.setClick((Long) rowArray[1]);
            views.add(view);
        }
        System.out.println(views);
        return views;
    }

    @GetMapping("/getAllNews")
    public Model getAllNews() {
//        return newsService.getAllNewsToIndex();
        return null;
    }

    @ApiOperation(value = "点击新闻")
    @GetMapping("/front/news/click/{id}")
    @ResponseBody
    public String clickArticleById(@PathVariable("id") Integer id) {
        newsService.clickArticleById(id);
        return "success";
    }

}
