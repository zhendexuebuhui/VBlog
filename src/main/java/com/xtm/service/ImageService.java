package com.xtm.service;

import com.xtm.dao.AdminRepository;
import com.xtm.dao.ArticleRepository;
import com.xtm.dao.ImageRepository;
import com.xtm.model.Admin;
import com.xtm.model.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author:藏剑
 * @date:2019/6/18 14:04
 */
@Service
@Slf4j
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public String updateCarousel(MultipartFile uploadFile, HttpServletRequest request, Integer index) throws IOException {
        HttpSession session = request.getSession();
        final String baseUrl = "asserts/images/";
        Image image = new Image();
        image.setId(index);//修改的轮播图索引
        String Url = "";
        if (!uploadFile.isEmpty()) {
            //图片保存路径
            String filepath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "static/" + baseUrl;
            File file = new File(filepath);
            //获取图片名称
            String fileName = uploadFile.getOriginalFilename();
            //设置uuid
            String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            fileName = uuid + fileName;
            log.info("轮播图上传:\nfileName:" + fileName);
            log.info("filepath:" + filepath);
            Url = baseUrl + fileName;
            image.setUrl(Url);
            image.setAddTime(new Date());
            //存储文件
            //上传到本地
            uploadFile.transferTo(new File(file, fileName));
            //数据库更新记录
            imageRepository.save(image);
        }
        return "上传成功";
    }

    public List<Image> getCarousels() {
        return imageRepository.getCarousels();
    }

    public Image getCarouselById(Integer id) {
        return imageRepository.findImagesById(id);
    }
}
