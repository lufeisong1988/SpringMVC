package com.jerry.repository;

import com.jerry.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lufeisong on 16/6/29.
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Transactional // 说明该方法是事务性操作
    @Modifying
    // 说明该方法是修改操作
    //定义查询
    //@Param注解用于提取参数
    @Query("update UserEntity us set us.nickname = :qNickname,us.firstName = :qFirstName,us.lastName = :qLastName,us.password = :qPassword where us.id = :qId")
    public void updateUser(@Param("qNickname") String nickname, @Param("qFirstName") String firstName,
                           @Param("qLastName") String lastName, @Param("qPassword") String password, @Param("qId") Integer id);
}
