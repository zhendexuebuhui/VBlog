package com.xtm.service;

import com.xtm.dao.MessageRepository;
import com.xtm.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author:藏剑
 * @date:2019/6/18 17:37
 */
@Service
@Transactional
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    public void deleteMessages(String[] ids) {
        for (String i : ids
        ) {
            messageRepository.deleteById(Integer.valueOf(i).intValue());
        }
    }

    public List<Message> getAllMessages(Integer adminId) {
        return messageRepository.findAllMessages(adminId);
    }

    public Message getMessageById(Integer id) {
        return messageRepository.findMessageById(id).get(0);
    }

    public void updateMessage(Message message) {
        messageRepository.saveAndFlush(message);
    }

    public Page<Message> findByContent(String content, int pageNo, int pageSize, Integer adminId) {
        if (pageNo == 0) {
            pageNo = 1;
        }
        PageRequest pageable = PageRequest.of(pageNo - 1, pageSize);
        return messageRepository.findBycontent(content, adminId, pageable);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    public void readMessages(String ids[]) {
        for (String i : ids
        ) {
            messageRepository.readMessage(Integer.valueOf(i).intValue());
        }
    }
}
