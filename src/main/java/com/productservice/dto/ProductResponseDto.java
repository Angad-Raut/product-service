package com.productservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductResponseDto {
    private Integer srNo;
    private Long id;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private String productStatus;
}
