package com.mstockRestAPI.mstockRestAPI.queries;

public class ProductQueries {

    public static final String FIND_BY_BARCODE_AND_IS_ACTIVE =
            "SELECT p FROM Product p " +
                    "JOIN p.productBarcodeList b " +
                    "WHERE b.barcode = :barcode " +
                    "AND " +
                    "p.isActive = :isActive";



    public static final String FIND_BY_PRODUCT_NAME_WITH_LIKE_AND_IS_ACTIVE =
            "SELECT p FROM Product p " +
                    "WHERE LOWER(p.productName) " +
                    "LIKE LOWER(CONCAT('%', :productName, '%')) " +
                    "AND " +
                    "p.isActive = :isActive";

}
