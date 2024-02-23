package com.mstockRestAPI.mstockRestAPI.service;


import com.mstockRestAPI.mstockRestAPI.dto.PaymentExtraInfoDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductMovementListDto;
import com.mstockRestAPI.mstockRestAPI.dto.SaleReceiptDto;
import com.mstockRestAPI.mstockRestAPI.entity.Customer;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentExtraInfo;
import com.mstockRestAPI.mstockRestAPI.entity.ProductMovements;
import com.mstockRestAPI.mstockRestAPI.entity.SaleReceipt;
import com.mstockRestAPI.mstockRestAPI.enums.Currency;
import com.mstockRestAPI.mstockRestAPI.enums.PaymentType;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.ProductMovementsRepository;
import com.mstockRestAPI.mstockRestAPI.repository.SaleReceiptRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.ProductMovementsServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CustomerCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.PaymentExtraInfoCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductMovementCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.SaleReceiptCreator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class ProductMovementsServiceTest {
    @Mock
    private Converter converter;
    @Mock
    private SaleReceiptRepository saleReceiptRepository;
    @Mock
    private ProductMovementsRepository productMovementsRepository;
    @InjectMocks
    private ProductMovementsServiceImpl productMovementsService;

    private SaleReceipt saleReceiptEntity;
    private SaleReceiptDto saleReceiptDto;
    private ProductMovements productMovementEntity;
    private ProductMovementDto productMovementDto;
    private ProductMovementListDto productMovementListDto;
    private List<ProductMovements> productMovementsList;
    private PaymentExtraInfo paymentExtraInfo;
    private PaymentExtraInfoDto paymentExtraInfoDto;
    private Customer customerEntity;


    @BeforeEach
    public void beforeEach(){
        saleReceiptEntity = SaleReceiptCreator.entity();
        productMovementEntity = ProductMovementCreator.entity();
        paymentExtraInfo = PaymentExtraInfoCreator.createEntity(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ONE,
                PaymentType.CASH

        );
        customerEntity = CustomerCreator.entity();
        saleReceiptDto = SaleReceiptCreator.dto();
        productMovementDto = ProductMovementCreator.dto();
        paymentExtraInfoDto = PaymentExtraInfoCreator.createDto(
                BigDecimal.TEN,
                BigDecimal.ONE,
                BigDecimal.ONE,
                PaymentType.CASH
        );
        productMovementsList = ProductMovementCreator.entityList();


    }

    @Test
    @DisplayName("Add Sales and create sale receipt")
    @Order(1)
    public void givenEntity_whenSave_thenReturnEntity(){
        // When
        when(converter.mapToEntity(any(), eq(PaymentExtraInfo.class))).thenReturn(paymentExtraInfo);
        when(converter.mapToEntity(any(), eq(Customer.class))).thenReturn(customerEntity);
        when(converter.mapToEntity(any(), eq(ProductMovements.class))).thenReturn(productMovementEntity);
        when(converter.mapToDto(any(), eq(SaleReceiptDto.class))).thenReturn(saleReceiptDto);

        when(saleReceiptRepository.save(any())).thenReturn(saleReceiptEntity);
        when(productMovementsRepository.saveAll(any())).thenReturn(productMovementsList);


        ProductMovementListDto wantsSave = ProductMovementListDto.builder()
                .paymentExtraInfo(paymentExtraInfoDto)
                .paymentType(PaymentType.CASH)
                .customer(CustomerCreator.dto())
                .receiptComment("Test comment")
                .productsList(ProductMovementCreator.dtoList())
                .build();
        SaleReceiptDto result = productMovementsService.addAllSalesOrReturnsProducts(wantsSave);

        // Assert
        assertNotNull(result);
    }




}
