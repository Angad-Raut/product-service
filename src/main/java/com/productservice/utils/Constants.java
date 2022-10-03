package com.productservice.utils;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final Boolean ACTIVE_STATUS=true;
    public static final Boolean DEACTIVE_STATUS=false;
    public static final String ENABLED="Enable";
    public static final String DISABLED="Disable";
    public static final String PRODUCT_ADDED="Product details added successfully!!";
    public static final String PRODUCT_UPDATED="Product details updated successfully!!";
    public static final String ENABLE_STATUS_UPDATED="Product enable successfully!!";
    public static final String DISABLE_STATUS_UPDATED="Product disable successfully!!";

    public static String setStatus(Boolean status) {
        if(status.equals(ACTIVE_STATUS)){
            return ENABLED;
        }else{
            return DISABLED;
        }
    }
}
