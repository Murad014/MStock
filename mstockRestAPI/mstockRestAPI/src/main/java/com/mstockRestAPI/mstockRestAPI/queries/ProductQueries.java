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

    public static final String FIND_BY_PRODUCT_NAME_AND_PRODUCT_BARCODE_AND_CATEGORY_AND_IS_ACTIVE =
            "SELECT p FROM Product p " +
                    "JOIN p.productBarcodeList b " +
                    "WHERE (:barcode IS NULL OR b.barcode = :barcode)" +
                    "AND (:productName IS NULL OR p.productName LIKE %:productName%) " +
                    "AND (:category IS NULL OR p.category.categoryName = :category) " +
                    "AND (:isActive IS NULL OR p.isActive = :isActive)";

}
