package com.xtm.controller;

import com.xtm.model.Admin;
import com.xtm.model.Image;
import com.xtm.service.AdminService;
import com.xtm.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author:藏剑
 * @date:2019/6/18 10:26
 */
@Controller
@Slf4j
@Api(tags = "轮播图")
public class CarouselController {

    @Autowired
    private ImageService imageService;

    @RequestMapping("/carousel.html")
    public ModelAndView carousels(HttpServletResponse response) {
        ModelAndView view = new ModelAndView("carousel");
        //获取轮播图
        List<Image> carousels = imageService.getCarousels();
        view.addObject("carouselList", carousels);
        return view;
    }

    @ApiOperation(value = "获取所有轮播图")
    @ResponseBody
    @GetMapping("/front/carousel")
    public List<Image> getCarousels() {
        List<Image> carousels = imageService.getCarousels();
        return carousels;

    }

    @ApiOperation(value = "根据id获取某张轮播图")
    @ResponseBody
    @GetMapping("/front/carousel/{id}")
    public Image getCarouselsById(@PathVariable("id") Integer id) {
        return imageService.getCarouselById(id);
    }

    @ResponseBody
    @PostMapping("/image/upload_carousel")
    /**
     *用于修改轮播图内容
     *@param:[uploadFile, request, index] index:轮播图索引
     *@return:java.lang.Object
     */
    public Object uploadImg(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request, Integer index) throws IOException {
        Map<String, String> map = new HashMap<>();
        String msg = imageService.updateCarousel(uploadFile, request, index);
        map.put("msg", msg);
        return map;
    }
}
