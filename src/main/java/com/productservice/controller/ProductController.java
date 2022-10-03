package com.productservice.controller;

import com.productservice.dto.EntityIdDto;
import com.productservice.dto.ProductRequestDto;
import com.productservice.dto.ProductResponseDto;
import com.productservice.dto.ResponseDto;
import com.productservice.exception.AlreadyExistException;
import com.productservice.exception.InvalidDataException;
import com.productservice.exception.ResourceNotFoundException;
import com.productservice.model.Product;
import com.productservice.serviceimpl.ProductDetailsService;
import com.productservice.utils.ErrorHandlerComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
      @Autowired
      private ProductDetailsService productDetailsService;
      @Autowired
      private ErrorHandlerComponent errorHandler;

      @PostMapping
      @RequestMapping(value = "/addProduct")
      public ResponseEntity<ResponseDto<String>> addProduct(@RequestBody ProductRequestDto requestDto, BindingResult result) {
            if (result.hasErrors()) {
                  return errorHandler.handleValidationErrors(result);
            }
            try {
                  return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(
                          productDetailsService.createOrupdate(requestDto),null),HttpStatus.CREATED);
            }catch(InvalidDataException | AlreadyExistException e) {
                  return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(null,
                          errorHandler.getMessageLocal(e.getMessage())),HttpStatus.OK);
            }
      }

      @PostMapping
      @RequestMapping(value = "/getProductById")
      public ResponseEntity<ResponseDto<Product>> getProduct(@RequestBody EntityIdDto requestDto, BindingResult result) {
            if (result.hasErrors()) {
                  return errorHandler.handleValidationErrors(result);
            }
            try {
                  return new ResponseEntity<ResponseDto<Product>>(new ResponseDto<Product>(
                          productDetailsService.getById(requestDto),null),HttpStatus.OK);
            }catch(ResourceNotFoundException e) {
                  return new ResponseEntity<ResponseDto<Product>>(new ResponseDto<Product>(null,
                          errorHandler.getMessageLocal(e.getMessage())),HttpStatus.OK);
            }
      }

      @PostMapping
      @RequestMapping(value = "/updateProductStatus")
      public ResponseEntity<ResponseDto<String>> updateProductStatus(@RequestBody EntityIdDto requestDto, BindingResult result) {
            if (result.hasErrors()) {
                  return errorHandler.handleValidationErrors(result);
            }
            try {
                  return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(
                          productDetailsService.updateStatus(requestDto),null),HttpStatus.OK);
            }catch(ResourceNotFoundException e) {
                  return new ResponseEntity<ResponseDto<String>>(new ResponseDto<String>(null,
                          errorHandler.getMessageLocal(e.getMessage())),HttpStatus.OK);
            }
      }

      @GetMapping
      @RequestMapping(value = "/getAllProducts")
      public ResponseEntity<ResponseDto<List<ProductResponseDto>>> addProduct() {
            return new ResponseEntity<ResponseDto<List<ProductResponseDto>>>(new ResponseDto<List<ProductResponseDto>>(
                    productDetailsService.getAllProducts(),null),HttpStatus.OK);
      }

}
