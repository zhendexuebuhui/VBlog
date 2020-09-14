package com.xtm.service;

import com.xtm.dao.AdminRepository;
import com.xtm.dao.ArticleRepository;
import com.xtm.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.ws.soap.Addressing;
import java.util.List;

/**
 * @author:藏剑
 * @date:2019/6/18 14:04
 */
@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ArticleRepository articleRepository;

    public Admin getAdmin(String account, String password) {
        List<Admin> admins = adminRepository.findByAcAndPa(account, password);
        if (admins.isEmpty()) {
            return null;
        } else
            return admins.get(0);

    }

    public void updateAdminHead(Admin admin) {
        adminRepository.updateAdminHead(admin);
    }

    public void updateAdmin(Admin oldAdmin, Admin admin) {
        adminRepository.updateAdmin(admin);
        articleRepository.updateAuthor(oldAdmin, admin);
    }

}
