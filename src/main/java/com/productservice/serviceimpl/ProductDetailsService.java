package com.productservice.serviceimpl;

import com.productservice.dto.EntityIdDto;
import com.productservice.dto.ProductRequestDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.exception.AlreadyExistException;
import com.productservice.exception.InvalidDataException;
import com.productservice.exception.ResourceNotFoundException;
import com.productservice.model.Product;
import com.productservice.repository.ProductRepository;
import com.productservice.service.ProductService;
import com.productservice.utils.Constants;
import com.productservice.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProductDetailsService implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ErrorCode errorCode;

    @Override
    public String createOrupdate(ProductRequestDto requestDto) throws AlreadyExistException, InvalidDataException {
        Product product = null;
        if (requestDto.getId()==null) {
              if(isProductExist(requestDto.getProductName())){
                 throw new AlreadyExistException(errorCode.ALREADY_PRODUCT_EXIST);
              }
              product = Product.builder()
                      .productName(requestDto.getProductName())
                      .productDescription(requestDto.getProductDescription())
                      .productPrice(requestDto.getProductPrice())
                      .productStatus(Constants.ACTIVE_STATUS)
                      .insertTime(new Date())
                      .updateTime(new Date())
                      .build();
        } else {
              Product product1 = productRepository.getProductById(requestDto.getId());
              if(!requestDto.getProductName().equals(product1.getProductName())){
                  if(isProductExist(requestDto.getProductName())){
                      throw new AlreadyExistException(errorCode.ALREADY_PRODUCT_EXIST);
                  }
                  product.setProductName(requestDto.getProductName());
              }
              if(!requestDto.getProductDescription().equals(product1.getProductDescription())){
                  product.setProductDescription(requestDto.getProductDescription());
              }
              if(!requestDto.getProductPrice().equals(product1.getProductPrice())) {
                  product.setProductPrice(requestDto.getProductPrice());
              }
              product.setUpdateTime(new Date());
        }
        try {
             productRepository.save(product);
             if(requestDto.getId()==null){
                  return Constants.PRODUCT_ADDED;
             }else{
                  return Constants.PRODUCT_UPDATED;
             }
        } catch (DataIntegrityViolationException e) {
             throw new InvalidDataException(errorCode.PRODUCT_INVALID);
        } catch (Exception e){
            throw new AlreadyExistException(e.getMessage());
        }
    }

    @Override
    public Product getById(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        Product product = productRepository.getProductById(entityIdDto.getEntityId());
        if(product==null){
            throw new ResourceNotFoundException(errorCode.PRODUCT_NOT_FOUND);
        }
        return product;
    }

    @Override
    public String updateStatus(EntityIdDto entityIdDto) throws ResourceNotFoundException {
        Product product = productRepository.getProductById(entityIdDto.getEntityId());
        if(product!=null){
             if(product.getProductStatus()){
                 productRepository.updateStatus(product.getId(),Constants.DEACTIVE_STATUS);
                 return Constants.DISABLE_STATUS_UPDATED;
             }else{
                 productRepository.updateStatus(product.getId(),Constants.ACTIVE_STATUS);
                 return Constants.ENABLE_STATUS_UPDATED;
             }
        }
        throw new ResourceNotFoundException(errorCode.PRODUCT_NOT_FOUND);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<ProductResponseDto> list = new ArrayList<ProductResponseDto>();
        Integer count=1;
        for (Product product: productRepository.findAll()) {
            list.add(ProductResponseDto.builder()
                    .srNo(count)
                    .id(product.getId())
                    .productName(product.getProductName())
                    .productDescription(product.getProductDescription())
                    .productPrice(product.getProductPrice())
                    .productStatus(Constants.setStatus(product.getProductStatus()))
                    .build());
            count++;
        }
        return list;
    }
    private Boolean isProductExist(String productName) {
         String product = productRepository.getProductName(productName);
         if(product!=null) {
             return Constants.ACTIVE_STATUS;
         }else{
             return Constants.DEACTIVE_STATUS;
         }
    }
}
