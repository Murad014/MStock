package com.mstockRestAPI.mstockRestAPI.service.impl;

import com.mstockRestAPI.mstockRestAPI.dto.*;
import com.mstockRestAPI.mstockRestAPI.entity.*;
import com.mstockRestAPI.mstockRestAPI.exception.FileUploadException;
import com.mstockRestAPI.mstockRestAPI.exception.ResourceNotFoundException;
import com.mstockRestAPI.mstockRestAPI.exception.SomethingWentWrongException;
import com.mstockRestAPI.mstockRestAPI.payload.converter.Converter;
import com.mstockRestAPI.mstockRestAPI.exception.SqlProcessException;
import com.mstockRestAPI.mstockRestAPI.payload.request.ProductSearchKeys;
import com.mstockRestAPI.mstockRestAPI.payload.response.ProductResponse;
import com.mstockRestAPI.mstockRestAPI.payload.response.SuccessResponse;
import com.mstockRestAPI.mstockRestAPI.repository.*;
import com.mstockRestAPI.mstockRestAPI.service.*;
import com.mstockRestAPI.mstockRestAPI.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Value("${upload.directory}") // Specify the directory where you want to save the uploaded files
    private String uploadDirectory;
    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductBarcodeRepository productBarcodeRepository;
    private final ProductPictureRepository productPictureRepository;
    private final ProductSalePricesRepository productSalePricesRepository;

    private final Converter converter;
    private final Util util;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            CompanyRepository companyRepository, ProductCategoryRepository productCategoryRepository,
            ProductBarcodeRepository productBarcodeRepository,
            ProductPictureRepository productPictureRepository,
            ProductSalePricesRepository productSalePricesRepository, Util util,
            Converter converter
    ){
        this.productRepository = productRepository;
        this.companyRepository = companyRepository;
        this.productCategoryRepository = productCategoryRepository;
        this.productBarcodeRepository = productBarcodeRepository;
        this.productPictureRepository = productPictureRepository;
        this.productSalePricesRepository = productSalePricesRepository;
        this.util = util;
        this.converter = converter;

    }

    @Override
    public ProductDto add(ProductDto productDto) throws SqlProcessException {
        Product entity = converter.mapToEntity(productDto, Product.class);

        findAndSet(productDto, entity);
        addBarcodesAndSet(productDto, entity);
        addPicturesAndSet(productDto, entity);
        addSalePricesAndSet(productDto, entity);

        Product productSave = productRepository.save(entity);

        return converter.mapToDto(productSave, ProductDto.class);
    }



    @Override
    @Transactional
    public ProductDto update(Long productId, ProductDto productDto) throws SqlProcessException {
        Product productFromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId.toString()));

        deleteAllReferences(productFromDB);
        findAndSet(productDto, productFromDB);

        addBarcodesAndSet(productDto, productFromDB);
        addPicturesAndSet(productDto, productFromDB);
        addSalePricesAndSet(productDto, productFromDB);

        Product productSave = productRepository.save(productFromDB);

        return converter.mapToDto(productSave, ProductDto.class);
    }



    @Override
    public SuccessResponse deActiveById(Long productId, Byte isActive) {
        Product findProductFromDB = productRepository.findById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productId.toString())
        );
        findProductFromDB.setIsActive(isActive);
        productRepository.save(findProductFromDB);
        return SuccessResponse
                .builder()
                .message("Success, id:" + productId)
                .build();
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

    @Override
    public ProductResponse searchAndPagination(ProductSearchKeys productSearchKeys) {
        Sort sort = productSearchKeys.getSortDir().equalsIgnoreCase(
                Sort.Direction.ASC.name()) ?
                Sort.by(productSearchKeys.getSortBy()).ascending() :
                Sort.by(productSearchKeys.getSortBy()).descending();


        Pageable pageable = PageRequest.of(
                productSearchKeys.getPageNo(),
                productSearchKeys.getPageSize(), sort);

        Page<Product> searchProduct = productRepository.findByBarcodeAndOtherFields(
                productSearchKeys.getBarcode(),
                productSearchKeys.getProductName(),
                productSearchKeys.getCategory(),
                productSearchKeys.getIsActive(),
                pageable
        );

        List<Product> listOfPosts = searchProduct.getContent();
        List<ProductDto> postList = listOfPosts.stream().map(product ->
                converter.mapToDto(product, ProductDto.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(postList);
        productResponse.setLast(searchProduct.isLast());
        productResponse.setPageNo(searchProduct.getNumber());
        productResponse.setPageSize(searchProduct.getSize());
        productResponse.setTotalElements(searchProduct.getTotalElements());
        productResponse.setTotalPages(searchProduct.getTotalPages());

        return productResponse;
    }


    // ----------------------------------- Not Override Methods --------------------------------------------
    private void deleteAllReferences(Product productFromDB) {

        productFromDB.getProductBarcodeList().forEach(
                productBarcodeRepository::delete
        );

        productFromDB.getProductSalePrices().forEach(
                productSalePricesRepository::delete
        );

        productFromDB.getProductPictureList().forEach(
                productPictureRepository::delete
        );
    }

    private void findAndSet(ProductDto productDto, Product save){
        save.setCompany(
                findCompanyById(productDto.getCompanyId())
        );
        save.setCategory(
                findByProductCategory(productDto.getProductCategoryId())
        );

    }

    private void addPicturesAndSet(ProductDto productDto, Product entity) {
        entity.setProductPictureList(new ArrayList<>());
        productDto.getProductPictureList().forEach(picture -> {
            if(!productPictureRepository.existsByPictureName(picture))
                    entity.getProductPictureList().add(
                            ProductPicture
                                    .builder()
                                    .pictureName(picture)
                                    .build()
                    );
                }
        );
    }

    private void addBarcodesAndSet(ProductDto productDto, Product entity) {
        entity.setProductBarcodeList(new ArrayList<>());
        productDto.getProductBarcodeList().forEach(barcode -> {
                if(!productBarcodeRepository.existsByBarcode(barcode))
                    entity.getProductBarcodeList().add(
                            ProductBarcode
                                    .builder()
                                    .barcode(barcode)
                                    .build()
                    );
                }
        );
    }

    private void addSalePricesAndSet(ProductDto productDto, Product entity) {
        entity.setProductSalePrices(new ArrayList<>());
        productDto.getProductSalePrices().forEach(salePrice ->
                entity.getProductSalePrices().add(
                        ProductSalePrice.builder()
                                .sellingPrice(salePrice)
                                .build()
                )
        );
    }



    private Company findCompanyById(Long companyId){
        return companyRepository.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException("Company", "id", companyId.toString())
        );
    }

    private ProductCategory findByProductCategory(Long productCategoryId){
        return productCategoryRepository.findById(productCategoryId).orElseThrow(
                () -> new ResourceNotFoundException("ProductCategory", "id", productCategoryId.toString())
        );
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
