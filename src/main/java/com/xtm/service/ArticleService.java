package com.xtm.service;

import com.xtm.dao.ArticleRepository;
import com.xtm.model.Article;
import com.xtm.model.ArticleAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:藏剑
 * @date:2019/6/18 17:37
 */
@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public void saveArticle(Article article) {
        articleRepository.save(article);
    }

    public void deleteArticles(String[] ids) {
        for (String i : ids
        ) {
            articleRepository.deleteById(Integer.valueOf(i).intValue());
        }
    }

    public List<Object> getAllArticlesByAuthorId(Integer authorId) {
        return articleRepository.findAllArticlesByAuthorId(authorId);
    }

    public List<Object> getAllArticles() {
        return articleRepository.findAllArticles();
    }

    public Article getArticleById(Integer id) {
        return articleRepository.findArticleById(id);
    }

    public ArticleAuthor getArticleAuthorById(Integer id) {
        List<Object> articleAuthorList = articleRepository.findArticleAuthorById(id);
        List<ArticleAuthor> views = new ArrayList<ArticleAuthor>();
        for (Object o : articleAuthorList) {
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
        return views.get(0);
    }


    public void updateArticle(Article article) {
        articleRepository.save(article);
    }

    public Page<Object> findByContent(String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return articleRepository.findByContent(content, pageable);
    }

    public Page<Object> findByContentAndAuthorId(String content, int pageNo, int pageSize, Integer authorId) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return articleRepository.findByContentAndAuthorId(content, authorId, pageable);
    }

    public List<Object> getAndSortClick() {
        return articleRepository.getAndSortClick();
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public List<Article> getAllArticlesToIndex() {
        return articleRepository.findAll();
    }

    public void clickArticleById(Integer id) {
        articleRepository.clickArticleById(id);
    }
}
