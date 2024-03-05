package com.mstockRestAPI.mstockRestAPI.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductBarcode;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.service.ProductBarcodeService;
import com.mstockRestAPI.mstockRestAPI.service.ProductCategoriesService;
import com.mstockRestAPI.mstockRestAPI.service.ProductService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCategoryCreator;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCreator;
import com.mstockRestAPI.mstockRestAPI.validation.impl.UniqueBarcodeValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import static com.mstockRestAPI.mstockRestAPI.tools.utils.Util.*;

@WebMvcTest(
        controllers =  ProductController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;
    @MockBean
    private ProductBarcodeService productBarcodeService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private ProductDto productDto;
    private List<ProductDto> productDtoList;
    private final String endPoint = "/api/v1/product/";

    @BeforeEach
    public void beforeEach(){
        this.productDto = ProductCreator.dto();
        this.productDtoList = ProductCreator.dtoList();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void givenDto_whenAdd_thenReturnDto() throws Exception {
        when(productService.add(any(ProductDto.class)))
                .thenReturn(productDto);

        ResultActions result = mockMvc.perform(
                post(endPoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
        );

        assertions(result, status().isCreated());

        verify(productService, times(1))
                .add(any(ProductDto.class));
    }

    @Test
    @DisplayName("Update")
    @Order(2)
    public void givenDto_whenUpdate_thenReturnDto() throws Exception{
        productDto.setId(1L);
        when(productService.update(anyLong(), any(ProductDto.class)))
                .thenReturn(productDto);

        ResultActions result = mockMvc.perform(
                put(endPoint + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto))
        );

        assertions(result, status().isOk());

        verify(productService, times(1))
                .update(anyLong(), any(ProductDto.class));
    }

    @Test
    @DisplayName("Add Product Pictures")
    @Order(3)
    public void givenProductIdAndPictures_whenAddPictures_thenReturnProductDto() throws Exception {
        // Arrange
        Long productId = 1L;
        MockMultipartFile picture =
                new MockMultipartFile("pictures", "test.jpg",MediaType.IMAGE_JPEG_VALUE, "test".getBytes());
        List<MultipartFile> files = new ArrayList<>();
        files.add(picture);
        when(productService.addPictures(productId, files)).thenReturn(createMockProductDto());

        // Act
        ResultActions result =  mockMvc.perform(
                multipart("/api/v1/product/{productId}/pictures",
                        productId).file(picture));

        // Assert
        verify(productService, times(1)).addPictures(productId, files);
    }

    @Test
    @DisplayName("Get All")
    @Order(4)
    public void getAllProducts() throws Exception {
        for(int i = 0; i < productDtoList.size(); i++)
            productDtoList.get(i)
                            .setId((long)i+1);

        // When
        when(productService.getAll()).thenReturn(productDtoList);

        // Act
        ResultActions result = mockMvc.perform(get(endPoint));

        // Convert
        List<ProductDto> responseBodyList = convertResultToList(result);

        // Assert
        assertNotNull(result);
        assertFalse(responseBodyList.isEmpty());
        assertEquals(productDtoList.size(), responseBodyList.size());
    }

    @ParameterizedTest
    @ValueSource(bytes = {0, 1})
    @DisplayName("Get All By isActive")
    @Order(5)
    public void getAllProductsByIsActive_whenFindAll_thenReturnList(byte isActive) throws Exception {
        // Arrange
        for (ProductDto dto : productDtoList) dto.setId(1L + 1);
        List<ProductDto> filterProuctList = productDtoList.stream()
                .filter(product -> product.getIsActive() == isActive)
                .toList();

        // When
        when(productService.getAllAndIsActive(isActive))
                .thenReturn(filterProuctList);

        // Send Request
        ResultActions result = mockMvc.perform(get(endPoint+"active/"+isActive));
        List<ProductDto> resultList = convertResultToList(result);

        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertEquals(filterProuctList.size(), resultList.size());
    }

    @Test
    @DisplayName("Get by ProductName and isActive")
    @Order(6)
    public void getAllProductByNameAndIsActive_whenFind_thenReturnDtoList() throws Exception {
        String productName = "a";
        byte isActive = 1;
        // Arrange
        List<ProductDto> filter = productDtoList.stream()
                .filter(product -> product.getIsActive() == isActive &&
                        product.getProductName().contains(productName))
                .toList();

        // When
        when(productService.getListByProductNameAndIsActive(productName, isActive))
                .thenReturn(filter);

        // Act
        ResultActions result = mockMvc.perform(get(
                String.format(endPoint + "name/%s", productName))
                .param("isActive", String.valueOf(isActive))
        );

        List<ProductDto> resultList = convertResultToList(result);

        assertNotNull(resultList);
        assertEquals(filter.size(), resultList.size());

    }




    private ProductDto createMockProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        return productDto;
    }

    private void assertions(ResultActions result, ResultMatcher resultMatcher) throws Exception {

        String id = "$.id";
        String productName = "$.productName";
        String barcode = "$.productBarcodeList[0]";
        String isActive = "$.isActive";

        result.andExpect(resultMatcher)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(id).value(productDto.getId()))
                .andExpect(jsonPath(productName).value(productDto.getProductName()))
                .andExpect(jsonPath(barcode).value(productDto.getProductBarcodeList().get(0)))
                .andExpect(jsonPath(isActive).value(String.valueOf(productDto.getIsActive())));

    }

}
