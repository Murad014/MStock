package com.mstockRestAPI.mstockRestAPI.service;

import com.mstockRestAPI.mstockRestAPI.dto.CreditOfCustomersDto;
import com.mstockRestAPI.mstockRestAPI.dto.PaymentCustomerCreditDto;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

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
    private CreditOfCustomersDto creditOfCustomerEntityDto;
    private PaymentCustomerCreditDto paymentCustomerCreditEntityDto;

    @BeforeEach
    public void beforeEach(){
        creditOfCustomerEntity = CreditOfCustomersCreator.entity();
        paymentCustomerCreditEntity = PaymentCustomerCreditCreator.entity();

        creditOfCustomerEntityDto = CreditOfCustomersCreator.dto();
        paymentCustomerCreditEntityDto = PaymentCustomerCreditCreator.dto();

    }

    @Test
    @DisplayName("Add credit payment")
    @Order(1)
    public void givenCreditIdAndEntity_whenAdd_thenReturnEntity(){
        Long creditId = 12L;
        creditOfCustomerEntity.setId(creditId);
        // When
        when(converter.mapToEntity(creditOfCustomerEntityDto, CreditOfCustomers.class))
                .thenReturn(creditOfCustomerEntity);
        when(converter.mapToDto(creditOfCustomerEntity, CreditOfCustomersDto.class))
                .thenReturn(creditOfCustomerEntityDto);
        when(converter.mapToEntity(paymentCustomerCreditEntityDto, PaymentCustomerCredit.class))
                .thenReturn(paymentCustomerCreditEntity);
        when(converter.mapToDto(paymentCustomerCreditEntity, PaymentCustomerCreditDto.class))
                .thenReturn(paymentCustomerCreditEntityDto);

        when(creditsOfCustomersRepository.findById(creditId)).thenReturn(Optional.ofNullable(creditOfCustomerEntity));
        when(paymentCustomerCreditRepository.save(paymentCustomerCreditEntity)).thenReturn(paymentCustomerCreditEntity);

        PaymentCustomerCreditDto payment =
                paymentCustomerCreditService.add(creditId, paymentCustomerCreditEntityDto);

        // Assert
        assertNotNull(payment);
        assertThat(payment)
                .usingRecursiveComparison()
                .isEqualTo(paymentCustomerCreditEntityDto);

        // Verify
        verify(converter, times(2)).mapToDto(any(), any());
        verify(converter, times(1)).mapToEntity(any(), any());
        verify(creditsOfCustomersRepository, times(1)).findById(any());
        verify(paymentCustomerCreditRepository, times(1)).save(any());

    }


}
