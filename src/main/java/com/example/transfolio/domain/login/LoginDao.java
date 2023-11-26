package com.example.transfolio.domain.login;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginDao {

    int SearchId(String id);
}
