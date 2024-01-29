package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.*;
import com.mstockRestAPI.mstockRestAPI.entity.ProductPicture;
import com.mstockRestAPI.mstockRestAPI.exception.FileUploadException;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.entity.Product;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.repository.*;
import com.mstockRestAPI.mstockRestAPI.service.*;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${upload.directory}") // Specify the directory where you want to save the uploaded files
    private String uploadDirectory;
    private final ProductRepository productRepository;

    private final Converter converter;
    private final Util util;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            Util util,
            Converter converter
    ){
        this.productRepository = productRepository;
        this.util = util;
        this.converter = converter;

    }

    @Override
    public ProductDto add(ProductDto productDto) throws SqlProcessException {
        if(!checkIdIsNull(productDto))
            throw new SqlProcessException("Product", "add", "every entity id must be null");

        Product entity = converter.mapToEntity(productDto, Product.class);
        Product productSave = productRepository.save(entity);

        return converter.mapToDto(productSave, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto) throws SqlProcessException {
        Long productId = productDto.getId();
        if(productId == null)
            throw new SqlProcessException("Product", "add", "every entity id must not be null");

        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));

        Product entity = converter.mapToEntity(productDto, Product.class);
        Product productSave = productRepository.save(entity);

        return converter.mapToDto(productSave, ProductDto.class);
    }


    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> converter.mapToDto(product, ProductDto.class))
                .toList();

    }

    @Override
    public ProductDto getById(Long id) {
        Product findById = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id.toString()));

        return converter.mapToDto(findById, ProductDto.class);
    }

    @Override
    public ProductDto getByBarcodeAndIsActive(String barcode, byte isActive) {
        List<Product> getProduct = productRepository.findByBarcode(barcode, isActive);

        if(getProduct.size() > 1)
            throw new SomethingWentWrongException("One barcode cannot be has more than one products");

        else if(getProduct.isEmpty())
            throw new ResourceNotFoundException("Product", "barcode", barcode + " and isActive=" + isActive);

        return converter.mapToDto(getProduct.get(0), ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllAndIsActive(byte isActive) {
        return productRepository.findByIsActive(isActive)
                .stream()
                .map(product -> converter.mapToDto(product, ProductDto.class))
                .toList();
    }

    @Override
    public List<ProductDto> getListByProductNameAndIsActive(String productName, byte isActive) {
        return productRepository.findByProductName(productName, isActive)
                .stream()
                .map(product-> converter.mapToDto(product, ProductDto.class))
                .toList();
    }

    @Override
    public ProductDto addPictures(Long productId, List<MultipartFile> pictures) {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));

        checkImageFiles(pictures);

        for(MultipartFile file: pictures){
            String pictureName = util.handleFileUpload(file,
                    uploadDirectory,
                    productId.toString());

            productFromDB.getProductPictureList().add(
                    ProductPicture.builder()
                            .pictureName(pictureName)
                            .build()
            );
        }

        return converter.mapToDto(
                productRepository.save(productFromDB),
                ProductDto.class);
    }


    // ----------------------------------- Not Override Methods --------------------------------------------
    private boolean checkIdIsNull(ProductDto productDto) {
        return isProductIdNull(productDto)
                && areBarcodeIdsNull(productDto.getProductBarcodeList())
                && areSalePriceIdsNull(productDto.getProductSalePrices())
                && arePictureIdsNull(productDto.getProductPictureList());
    }

    private boolean isProductIdNull(ProductDto productDto) {
        return productDto.getId() == null;
    }

    private boolean areBarcodeIdsNull(List<ProductBarcodeDto> barcodeList) {
        return barcodeList == null || barcodeList.stream().noneMatch(barcode -> barcode.getId() != null);
    }

    private boolean areSalePriceIdsNull(List<ProductSalePriceDto> salePriceList) {
        return salePriceList == null || salePriceList.stream().noneMatch(salePrice -> salePrice.getId() != null);
    }

    private boolean arePictureIdsNull(List<ProductPictureDto> pictureList) {
        return pictureList == null || pictureList.stream().noneMatch(picture -> picture.getId() != null);
    }

    private void checkImageFiles(List<MultipartFile> pictures){
        for(MultipartFile file: pictures) {
            boolean isImage = util.isImageFile(file);
            if (!isImage)
                throw new FileUploadException(file.getOriginalFilename(), "only image files");
        }
    }
    // ----------------------------------- END - Not Override Methods --------------------------------------------


}
