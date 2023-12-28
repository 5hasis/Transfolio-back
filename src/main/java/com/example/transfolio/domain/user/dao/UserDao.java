package com.example.transfolio.domain.user.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserDao {

    int insertUserInfo(HashMap body);

    List<HashMap> selectByUserId(HashMap param);

}
