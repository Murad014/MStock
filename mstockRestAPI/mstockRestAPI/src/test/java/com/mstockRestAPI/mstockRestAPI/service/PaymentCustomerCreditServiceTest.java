package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.entity.CreditOfCustomers;
import com.mstockRestAPI.mstockRestAPI.entity.PaymentCustomerCredit;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.repository.CreditsOfCustomersRepository;
import com.mstockRestAPI.mstockRestAPI.repository.CustomerRepository;
import com.mstockRestAPI.mstockRestAPI.repository.PaymentCustomerCreditRepository;
import com.mstockRestAPI.mstockRestAPI.service.impl.PaymentCustomerCreditServiceImpl;
import com.mstockRestAPI.mstockRestAPI.tools.creator.CreditOfCustomersCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.PaymentCustomerCreditCreator;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource(locations = "/application-test.properties")
public class PaymentCustomerCreditServiceTest {
    @Mock
    private Converter converter;

    @Mock
    private PaymentCustomerCreditRepository paymentCustomerCreditRepository;
    @Mock
    private CreditsOfCustomersRepository creditsOfCustomersRepository;

    @InjectMocks
    private PaymentCustomerCreditServiceImpl paymentCustomerCreditService;

    private CreditOfCustomers creditOfCustomerEntity;
    private PaymentCustomerCredit paymentCustomerCreditEntity;

    @BeforeEach
    public void beforeEach(){
        creditOfCustomerEntity = CreditOfCustomersCreator.entity();
        paymentCustomerCreditEntity = PaymentCustomerCreditCreator.entity();
    }
//
//    @Test
//    @DisplayName("Add credit payment")
//    @Order(1)
//    public void givenCreditIdAndEntity_whenAdd_thenReturnEntity(){
//        singleMock(entity, dto);
//    }
//    private void singleMock(CreditOfCustomers entity, CreditOfCustomersDto dto){
//        when(converter.mapToEntity(dto, CreditOfCustomers.class))
//                .thenReturn(entity);
//        when(converter.mapToDto(entity, CreditOfCustomersDto.class))
//                .thenReturn(dto);
//    }
//


}
