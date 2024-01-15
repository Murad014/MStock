package com.mstockRestAPI.mstockRestAPI.tools.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import java.util.*;
import java.io.UnsupportedEncodingException;

public class Util {

    public static String convertApiResultToString(ResultActions result) throws UnsupportedEncodingException {
        MvcResult mvcResult = result.andReturn();
        return mvcResult.getResponse().getContentAsString();
    }

    public static <T> List<T> convertResultToList(ResultActions result) throws UnsupportedEncodingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(
                convertApiResultToString(result),
                new TypeReference<>() {});
    }

}
