package com.mstockRestAPI.mstockRestAPI.payload.request;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import static com.mstockRestAPI.mstockRestAPI.cons.Constans.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSearchKeys {
    private int pageNo;
    private int pageSize;
    private String sortBy;
    private String sortDir;
    private String barcode;
    private String productName;
    private String category;
    private String isActive;

}
