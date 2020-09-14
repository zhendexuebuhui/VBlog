package com.xtm.dao;

import com.xtm.model.Admin;
import com.xtm.model.Article;
import com.xtm.model.ArticleAuthor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author:藏剑
 * @date:2019/6/18 17:18
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    List<Article> findAll();

    @Modifying
    @Query(value = "insert into article(author_id,content,create_time,title) values(:#{#article.authorId},:#{#article.content},:#{#article.createTime},:#{#article.title})", nativeQuery = true)
    void saveArticle(Article article);

    @Query(value = " select at.id,ad.adminName,at.content,at.createTime,at.title,ad.account,at.click from Article at,Admin ad  where  at.authorId=?1 and ad.id=at.authorId")
    List<Object> findAllArticlesByAuthorId(Integer authorId);


    @Query(value = " select at.id,ad.adminName,at.content,at.createTime,at.title,ad.account,at.click from Article at,Admin ad  where ad.id=at.authorId")
    List<Object> findAllArticles();

    Article findArticleById(Integer id);

    @Modifying
    @Query(value = "update article set title=:#{#article.title},content=:#{#article.content}  where id=:#{#article.id}", nativeQuery = true)
    void updateArticle(Article article);

    @Modifying
    @Query(value = "update article set author=:#{#admin.adminName}  where author=:#{#oldAdmin.adminName}", nativeQuery = true)
    void updateAuthor(Admin oldAdmin, Admin admin);

    /*@Modifying
    @Query(value = "delete from article where article_id=?1",nativeQuery = true)
    void deleteArticle(Integer id);*/

    /**
     * 模糊查询信息
     */
    @Query(value = "select at.id,ad.adminName,at.content,at.createTime,at.title,ad.account,at.click from Article at,Admin ad  where at.title like CONCAT('%',?1,'%') or at.content like CONCAT('%',?1,'%') and at.authorId=?2 and ad.id=at.authorId ")
    Page<Object> findByContentAndAuthorId(String content, Integer authorId, Pageable pageable);

    /**
     * 模糊查询信息
     */
    @Query(value = "select at.id,ad.adminName,at.content,at.createTime,at.title,ad.account,at.click from Article at,Admin ad  where (at.title like CONCAT('%',?1,'%') or ad.adminName like CONCAT('%',?1,'%') or at.content like CONCAT('%',?1,'%'))  and ad.id=at.authorId ")
    Page<Object> findByContent(String content, Pageable pageable);

    /**
     * 用于统计用户点击量
     *
     * @param:
     * @return:
     */
    @Query(value = "select ad.adminName,sum(at.click) as clickSum from Article at,Admin ad where at.authorId=ad.id  group by at.authorId order by clickSum desc ")
    List<Object> getAndSortClick();

    @Query(value = " select at.id,ad.adminName,at.content,at.createTime,at.title,ad.account,at.click from Article at,Admin ad  where  at.id=?1 and ad.id=at.authorId")
    List<Object> findArticleAuthorById(Integer id);

    @Modifying
    @Query(value = "update Article at set at.click=at.click+1 where at.id=?1")
    void clickArticleById(Integer id);
}
