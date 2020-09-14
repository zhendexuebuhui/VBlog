package com.xtm.dao;

import com.xtm.model.Message;
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
public interface MessageRepository extends JpaRepository<Message, Integer> {
   /* @Modifying
    @Query(value = "insert into message(author,content,create_time,title) values(:#{#message.author},:#{#message.content},:#{#message.createTime},:#{#message.title})",nativeQuery = true)
    void saveMessage(Message message);*/


    @Query(value = "select * from message where admin_id=?1", nativeQuery = true)
    List<Message> findAllMessages(Integer adminId);

    @Query(value = "select * from message at where at.message_id=?1", nativeQuery = true)
    List<Message> findMessageById(Integer id);

    /*@Modifying
    @Query(value = "update message set title=:#{#message.title},content=:#{#message.content}  where id=:#{#message.id}",nativeQuery = true)
    void updateMessage(Message message);*/

   /* @Modifying
    @Query(value = "delete from message where message_id=?1",nativeQuery = true)
    void deleteMessage(Integer id);*/

    @Modifying
    @Query(value = "update message set read_status=1 where message_id=?1 ", nativeQuery = true)
    void readMessage(Integer id);

    /**
     * 模糊查询信息
     */
    @Query(value = "select ms from Message ms where   ms.strangerName like CONCAT('%',?1,'%') or ms.content like CONCAT('%',?1,'%') and ms.adminId=?2")
    Page<Message> findBycontent(String content, Integer adminId, Pageable pageable);

}
