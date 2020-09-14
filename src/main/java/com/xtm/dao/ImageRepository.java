package com.xtm.dao;

import com.xtm.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:藏剑
 * @date:2019/8/20 12:34
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Query(value = "select im from Image im where im.id>=1 and im.id<=5 order by im.id")
    List<Image> getCarousels();

    Image findImagesById(Integer id);
}
