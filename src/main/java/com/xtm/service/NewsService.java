package com.xtm.service;

import com.xtm.dao.NewsRepository;
import com.xtm.model.ArticleAuthor;
import com.xtm.model.News;
import com.xtm.model.NewsAuthor;
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
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public void saveNews(News news) {
        newsRepository.save(news);
    }

    public void deleteNewss(String[] ids) {
        for (String i : ids
        ) {
            newsRepository.deleteById(Integer.valueOf(i).intValue());
        }
    }

    public List<Object> getAllNewssByAuthorId(Integer authorId) {
        return newsRepository.findAllNewssByAuthorId(authorId);
    }

    public List<Object> getAllNewss() {
        return newsRepository.findAllNewss();
    }

    public List<News> getNewss() {
        return newsRepository.findAll();
    }

    public News getNewsById(Integer id) {
        return newsRepository.findNewsById(id);
    }

    public void updateNews(News news) {
        newsRepository.save(news);
    }

    public Page<Object> findByContent(String content, int pageNo, int pageSize) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return newsRepository.findByContent(content, pageable);
    }

    public Page<Object> findByContentAndAuthorId(String content, int pageNo, int pageSize, Integer authorId) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return newsRepository.findByContentAndAuthorId(content, authorId, pageable);
    }

    public List<Object> getAndSortClick() {
        return newsRepository.getAndSortClick();
    }

    public List<News> getAllNewssToIndex() {
        return newsRepository.findAll();
    }

    public NewsAuthor getNewsAuthorById(Integer id) {
        List<Object> newsAuthorList = newsRepository.findNewsAuthorById(id);
        List<NewsAuthor> views = new ArrayList<NewsAuthor>();
        for (Object o : newsAuthorList) {
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
        return views.get(0);
    }

    public void clickArticleById(Integer id) {
        newsRepository.clickNewsById(id);
    }
}
