package com.mstockRestAPI.mstockRestAPI.tools.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.text.DecimalFormat;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

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

    public static double generateRandomPrice(double minPrice, double maxPrice) {
        Random random = new Random();
        String priceStr = formatPrice(minPrice + (maxPrice - minPrice) * random.nextDouble());
        return Double.parseDouble(priceStr);
    }

    public static String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(price);
    }

    public static String generateRandomBarcode(int length) {
        return new Random().ints(length, '0', '9' + 1)
                .mapToObj(i -> String.valueOf((char) i))
                .collect(Collectors.joining());
    }


    public static <T extends Enum<T>> T chooseRandomEnum(Class<T> enumType) {
        T[] values = enumType.getEnumConstants();
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Enum class must have constants");
        }

        Random random = new Random();
        int index = random.nextInt(values.length);

        return values[index];
    }

    public static String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));
        }

        return cardNumber.toString();
    }

}
