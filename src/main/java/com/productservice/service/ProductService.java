package com.productservice.service;

import com.productservice.dto.EntityIdDto;
import com.productservice.dto.ProductRequestDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.exception.AlreadyExistException;
import com.productservice.exception.InvalidDataException;
import com.productservice.exception.ResourceNotFoundException;
import com.productservice.model.Product;

import java.util.List;

public interface ProductService {
    String createOrupdate(ProductRequestDto requestDto)throws AlreadyExistException, InvalidDataException;
    Product getById(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    String updateStatus(EntityIdDto entityIdDto)throws ResourceNotFoundException;
    List<ProductResponseDto> getAllProducts();
}
