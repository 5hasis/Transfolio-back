package com.example.transfolio.domain.login;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface LoginDao {

    HashMap SearchId(HashMap body);
}
