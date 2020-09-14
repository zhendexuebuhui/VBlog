package com.xtm.controller;

import com.xtm.dao.ArticleRepository;
import com.xtm.model.*;
import com.xtm.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.util.*;

/**
 * @author:藏剑
 * @date:2019/6/18 10:26
 */
@Slf4j
@Controller
@Api(tags = "文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/article")
    public String saveArticle(Article article) {
        article.setCreateTime(new Date());
        articleService.saveArticle(article);
        return "redirect:/listArticle.html";
    }

    @DeleteMapping("/article")
    @ResponseBody
    public String deleteArticle(String ids) {
        log.info("要删除的文章id:" + ids);
        String[] sids = ids.split(",");
        articleService.deleteArticles(sids);
        return "{\"msg\": \"删除成功\"}";
    }

    @GetMapping("/article/{id}")
    public String articleUpdate(@PathVariable("id") Integer id, Model model) {
        Article article = articleService.getArticleById(id);
        log.info(article.toString());
        model.addAttribute("article", article);
        return "article/modifyArticle";
    }

    @ApiOperation(value = "获取所有文章")
    @ResponseBody
    @GetMapping("/front/articles")
    public List<ArticleAuthor> getArticles() {
        List<Object> articles = articleService.getAllArticles();
        List<ArticleAuthor> views = new ArrayList<ArticleAuthor>();
        for (Object o : articles) {
            Object[] rowArray = (Object[]) o;
            ArticleAuthor view = new ArticleAuthor();
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

    @ApiOperation(value = "根据id获取某篇文章")
    @ResponseBody
    @GetMapping("/front/article/{id}")
    public ArticleAuthor getArticleById(@PathVariable("id") Integer id) {
        ArticleAuthor articleAuthor = articleService.getArticleAuthorById(id);
        return articleAuthor;

    }


    @ResponseBody
    @GetMapping("/articles")
    public Map<String, Object> getArticles(HttpServletRequest request, @RequestParam(value = "content", required = false) String content) {
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
            List<Object> articles = articleService.getAllArticles();
            List<ArticleAuthor> views = new ArrayList<ArticleAuthor>();
            for (Object o : articles) {
                Object[] rowArray = (Object[]) o;
                ArticleAuthor view = new ArticleAuthor();
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
            Page<Object> articles = articleService.findByContent(content, pageNumber, pageSize);
            List<ArticleAuthor> views = new ArrayList<ArticleAuthor>();
            for (Object o : articles) {
                Object[] rowArray = (Object[]) o;
                ArticleAuthor view = new ArticleAuthor();
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
//            JSONArray json = JSONArray.fromObject(articles.getContent());
            result.put("data", views);
        }
        //返回layui数据表格需要的json数据
        result.put("code", 0);
        result.put("msg", "");
        log.info(result.toString());
        return result;

    }

    @PutMapping("/article")
    @ResponseBody
    public String updateArticle(Article article) {
        articleService.updateArticle(article);
        return "success";
    }

    @GetMapping("/sortArticleClick")
    @ResponseBody
    public List<AdminClick> getAndSortClick() {
        List<Object> userAndClicks = articleService.getAndSortClick();
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

    @GetMapping("/getAllArticles")
    public Model getAllArticles() {
//        return articleService.getAllArticlesToIndex();
        return null;
    }

    @ApiOperation(value = "点击文章")
    @GetMapping("/front/article/click/{id}")
    @ResponseBody
    public String clickArticleById(@PathVariable("id") Integer id) {
        articleService.clickArticleById(id);
        return "success";
    }

}
