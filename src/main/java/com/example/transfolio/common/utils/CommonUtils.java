package com.example.transfolio.common.utils;

import com.example.transfolio.common.error.ErrorEnum;
import com.example.transfolio.common.error.ErrorMessage;
import com.example.transfolio.common.error.ErrorObj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CommonUtils {

    /**
     * 요청받은 Hashmap Parameter 검증
     * @param param
     */

    public static boolean validRequestParam(HashMap param) {

         // 공백 및 Null 검사
         if (isNullOrEmpty(param)) {
             return true;
         }

         // Parameter Size 검사
         if (param.size() == 0) {
             return true;
         }

         return false;
    }

    /**
     * 요청받은 Hashmap Parameter 검증
     * validKey 배열을 통해 해당 배열의 Key가 존재하지 않은경우 true
     * EX). String[] validKey = { "id", "password", "email" };
     * @param param
     */

    public static boolean validRequestParam(HashMap param, String[] validKeys) {

        if (validRequestParam(param)) {
            return true;
        }

        // validKeys에 Key가 존재하지 않을경우
        boolean isValidKey = Arrays.stream(validKeys)
                .anyMatch(validKey -> isNullOrEmpty(param.get(validKey)));
        if (isValidKey) {
            return true;
        }

        return false;
    }

    /**
     * Parameter Null 및 공백 유효성 검사
     * @param obj
     * @return
     */
    public static boolean isNullOrEmpty(Object obj) {
        return "".equals(obj) || obj == null;
    }

    /**
     * List의 사이즈가 0일경우
     */
    public static boolean isZeroSize(List list) {
        return list.size() == 0;
    }

    /**
     * List의 사이즈가 2 이상일경우
     */
    public static boolean isTwoOverSize(List list) {
        return list.size() >= 2;
    }
}
