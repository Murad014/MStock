package com.mstockRestAPI.mstockRestAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstockRestAPI.mstockRestAPI.dto.CompanyDto;
import com.mstockRestAPI.mstockRestAPI.dto.ProductCategoryDto;
import com.mstockRestAPI.mstockRestAPI.entity.ProductCategory;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.service.ProductCategoriesService;
import com.mstockRestAPI.mstockRestAPI.tools.creator.ProductCategoryCreator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static com.mstockRestAPI.mstockRestAPI.tools.utils.Util.*;

@WebMvcTest(
        controllers =  ProductCategoryController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductCategoryControllerTest {

    @MockBean
    private ProductCategoriesService productCategoriesService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private ProductCategoryDto productCategoryDto;
    private List<ProductCategoryDto>  productCategoryDtoList;

    private final String updatedDate = "$.updatedDate";
    private final String id = "$.id";
    private final String endPoint = "/api/v1/productCategory/";

    @BeforeEach
    public void beforeEach(){
        this.productCategoryDto = ProductCategoryCreator.createProductCategoryDto();
        this.productCategoryDtoList = ProductCategoryCreator.createProductCategoryDtoList();
    }

    @Test
    @DisplayName("Add")
    @Order(1)
    public void saveProductCategory_whenAdd_thenReturnDto() throws Exception {
        when(productCategoriesService.add(any(ProductCategoryDto.class)))
                .thenReturn(productCategoryDto);

        ResultActions result = mockMvc.perform(post(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCategoryDto)));


        // Then
        assertions(result, status().isCreated());

        verify(productCategoriesService, times(1))
                .add(any(ProductCategoryDto.class));

    }

    @Test
    @DisplayName("Update by id")
    @Order(2)
    public void updateProductCategory_whenUpdate_thenReturnDto() throws Exception {
        when(productCategoriesService.update(any(ProductCategoryDto.class)))
                .thenReturn(productCategoryDto);
        ResultActions result = mockMvc.perform(put(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCategoryDto)));

        assertions(result, status().isOk());

        verify(productCategoriesService, times(1))
                .update(any(ProductCategoryDto.class));

    }

    @Test
    @DisplayName("Update Not exist id")
    @Order(3)
    public void updateProductCategoryDoesNotExistId_whenTryUpdate_thenReturnException() throws Exception {
        when(productCategoriesService.update(any(ProductCategoryDto.class)))
                .thenThrow(ResourceNotFoundException.class);

        ResultActions result = mockMvc.perform(put(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productCategoryDto)));

        result.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(productCategoriesService, times(1))
                .update(any(ProductCategoryDto.class));

    }

    @Test
    @DisplayName("Get All")
    @Order(4)
    public void getAll_whenGetAll_thenReturnListDto() throws Exception {
        // When
        when(productCategoriesService.getAll())
                .thenReturn(productCategoryDtoList);

        // Act
        ResultActions result = mockMvc.perform(get(endPoint));


        //Convert
        MvcResult mvcResult = result.andReturn();
        String responseBody = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductCategoryDto> responseBodyList =
                objectMapper.readValue(responseBody, new TypeReference<>() {});

        // Assert
        assertNotNull(responseBodyList);
        assertFalse(responseBodyList.isEmpty());
        assertEquals(responseBodyList.size(), productCategoryDtoList.size());
        assertionsBulk(responseBodyList);

        // Verify
        verify(productCategoriesService, times(1)).getAll();

    }

    @Test
    @DisplayName("Get By Id")
    @Order(5)
    public void getById_whenFind_thenReturnDto() throws Exception {
        Long existId = 1L;
        // When
        when(productCategoriesService.getById(existId)).thenReturn(productCategoryDto);

        // Act
        ResultActions resultActions = mockMvc.perform(get(endPoint + existId)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        assertions(resultActions, status().isOk());

        // Verify
        verify(productCategoriesService, times(1)).getById(existId);
    }

    @Test
    @DisplayName("Get Does not exist Id")
    @Order(6)
    public void tryGetDoesNotExist_thenReturnException() throws Exception {
        Long doesNotExist = 1L;

        // When
        when(productCategoriesService.getById(doesNotExist)).thenThrow(
                new ResourceNotFoundException("Resource not found!", "id", doesNotExist.toString())
        );

        ResultActions result = mockMvc.perform(get(endPoint + doesNotExist)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Get by Company Name")
    @Order(7)
    public void tryGetProductCategory_whenFind_thenReturnDto() throws Exception {
        String fullEndPoint = endPoint.concat("/categoryName/pen");

        // When
        when(productCategoriesService.findByCategoryName(anyString()))
                .thenReturn(productCategoryDto);

        ResultActions result = mockMvc.perform(get(fullEndPoint)
                .contentType(MediaType.APPLICATION_JSON));

        // Assert
        assertions(result, status().isOk());

        // Verify
        verify(productCategoriesService, times(1)).findByCategoryName(anyString());
    }

    @Test
    @DisplayName("Get All where actives")
    @Order(8)
    public void getAllProductCategoriesWhereIsActive_whenFind_thenReturnDtoList() throws Exception {
        Byte isActive = 1;

        String fullPoint = endPoint.concat("isActive/" + isActive);

        // Filter
        List<ProductCategoryDto> activeList = getWhereIsActive(isActive);

        // When
        when(productCategoriesService.findByIsActive(isActive)).thenReturn(activeList);

        ResultActions result = mockMvc.perform(get(fullPoint));

        List<ProductCategoryDto> resultList = convertResultToList(result);

        // Assert
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertEquals(activeList.size(), resultList.size());
    }

    @Test
    @DisplayName("Get All where deactivates")
    @Order(9)
    public void getAllProductCategoriesWhereIsNotActive_whenFind_thenReturnDtoList() throws Exception {
        Byte isActive = 0;

        String fullPoint = endPoint.concat("isActive/" + isActive);

        // Filter
        List<ProductCategoryDto> activeList = getWhereIsActive(isActive);


        // When
        when(productCategoriesService.findByIsActive(isActive)).thenReturn(activeList);

        ResultActions result = mockMvc.perform(get(fullPoint));

        List<ProductCategoryDto> resultList = convertResultToList(result);

        // Assert
        assertNotNull(resultList);
        assertFalse(resultList.isEmpty());
        assertEquals(activeList.size(), resultList.size());
    }




    private List<ProductCategoryDto> getWhereIsActive(Byte isActive){
        return
                productCategoryDtoList.stream()
                        .filter(dto -> Objects.equals(dto.getIsActive(), isActive))
                        .toList();
    }


    private void assertions(ResultActions result, ResultMatcher resultMatcher) throws Exception {
        // Keys
        String categoryName = "$.categoryName";
        String description = "$.description";
        String isActive = "$.isActive";

        result.andExpect(resultMatcher)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(categoryName).value(productCategoryDto.getCategoryName()))
                .andExpect(jsonPath(description).value(productCategoryDto.getDescription()))
                .andExpect(jsonPath(isActive).value(productCategoryDto.getIsActive().toString()));
    }

    private void assertionsBulk(List<ProductCategoryDto> responseBodyList){
        assertAll("ProductCategories",
                () -> IntStream.range(0, productCategoryDtoList.size())
                        .forEach(i -> {
                            ProductCategoryDto expected = productCategoryDtoList.get(i);
                            ProductCategoryDto actual = responseBodyList.get(i);

                            assertAll("ProductCategory " + i,
                                    () -> assertEquals(expected.getId(), actual.getId()),
                                    () -> assertEquals(expected.getCategoryName(), actual.getCategoryName()),
                                    () -> assertEquals(expected.getDescription(), actual.getDescription()),
                                    () -> assertEquals(expected.getIsActive().toString(), actual.getIsActive().toString())
                            );
                        })
        );
    }




}
