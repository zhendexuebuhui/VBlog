package com.xtm;

import com.xtm.dao.AdminRepository;
import com.xtm.dao.ArticleRepository;
import com.xtm.model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VBlogApplicationTests {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void contextLoads() {
    }

}
