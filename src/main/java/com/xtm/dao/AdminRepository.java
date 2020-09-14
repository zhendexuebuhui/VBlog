package com.xtm.dao;

import com.xtm.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author:藏剑
 * @date:2019/6/18 14:02
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    @Query(value = "select * from admin  where account=?1 and password=?2 ", nativeQuery = true)
    List<Admin> findByAcAndPa(String account, String password);

    @Modifying
    @Query(value = "update admin set head_url=:#{#admin.headUrl} where admin_id=:#{#admin.id}", nativeQuery = true)
    void updateAdminHead(Admin admin);

    @Modifying
    @Query(value = "update admin set  password=:#{#admin.password},admin_name=:#{#admin.adminName},profile=:#{#admin.profile} where id=:#{#admin.id}", nativeQuery = true)
    void updateAdmin(Admin admin);

}
