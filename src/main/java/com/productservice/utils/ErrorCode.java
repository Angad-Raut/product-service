package com.productservice.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:/error-code.properties")
public class ErrorCode {
	@Value("${PRODUCT_INVALID}") public String PRODUCT_INVALID;
	@Value("${ALREADY_PRODUCT_EXIST}") public String ALREADY_PRODUCT_EXIST;
	@Value("${PRODUCT_NOT_FOUND}") public String PRODUCT_NOT_FOUND;
}
