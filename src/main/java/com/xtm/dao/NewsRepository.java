package com.xtm.dao;

import com.xtm.model.Admin;
import com.xtm.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:藏剑
 * @date:2019/6/18 17:18
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    List<News> findAll();

    @Modifying
    @Query(value = "insert into news(author_id,content,create_time,title) values(:#{#news.authorId},:#{#news.content},:#{#news.createTime},:#{#news.title})", nativeQuery = true)
    void saveNews(News news);

    @Query(value = " select ne.id,ad.adminName,ne.content,ne.createTime,ne.title,ad.account,ne.click from News ne,Admin ad  where  ne.authorId=?1 and ad.id=ne.authorId")
    List<Object> findAllNewssByAuthorId(Integer authorId);

    @Query(value = " select ne.id,ad.adminName,ne.content,ne.createTime,ne.title,ad.account,ne.click from News ne,Admin ad  where ad.id=ne.authorId")
    List<Object> findAllNewss();

    News findNewsById(Integer id);

    @Modifying
    @Query(value = "update news set title=:#{#news.title},content=:#{#news.content}  where id=:#{#news.id}", nativeQuery = true)
    void updateNews(News news);

    @Modifying
    @Query(value = "update news set author=:#{#admin.adminName}  where author=:#{#oldAdmin.adminName}", nativeQuery = true)
    void updateAuthor(Admin oldAdmin, Admin admin);

    /*@Modifying
    @Query(value = "delete from news where news_id=?1",nativeQuery = true)
    void deleteNews(Integer id);*/

    /**
     * 模糊查询信息
     */
    @Query(value = "select ne.id,ad.adminName,ne.content,ne.createTime,ne.title,ad.account,ne.click from News ne,Admin ad  where ne.title like CONCAT('%',?1,'%') or ne.content like CONCAT('%',?1,'%') and ne.authorId=?2 and ad.id=ne.authorId ")
    Page<Object> findByContentAndAuthorId(String content, Integer authorId, Pageable pageable);

    /**
     * 模糊查询信息
     */
    @Query(value = "select ne.id,ad.adminName,ne.content,ne.createTime,ne.title,ad.account,ne.click from News ne,Admin ad  where (ne.title like CONCAT('%',?1,'%') or ad.adminName like CONCAT('%',?1,'%') or ne.content like CONCAT('%',?1,'%'))  and ad.id=ne.authorId ")
    Page<Object> findByContent(String content, Pageable pageable);

    /**
     * 用于统计用户点击量
     *
     * @param:
     * @return:
     */
    @Query(value = "select ad.adminName,sum(ne.click) as clickSum from News ne,Admin ad where ne.authorId=ad.id  group by ne.authorId order by clickSum desc ")
    List<Object> getAndSortClick();

    @Query(value = " select ne.id,ad.adminName,ne.content,ne.createTime,ne.title,ad.account,ne.click from News ne,Admin ad  where  ne.id=?1 and ad.id=ne.authorId")
    List<Object> findNewsAuthorById(Integer id);

    @Modifying
    @Query(value = "update News ne set ne.click=ne.click+1 where ne.id=?1")
    void clickNewsById(Integer id);
}
