package com.productservice.repository;

import com.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "select * from product where id=:productId",nativeQuery = true)
    Product getProductById(@Param("productId")Long productId);

    @Query(value = "select product_name from product where product_name=:productName",nativeQuery = true)
    String getProductName(@Param("productName")String productName);

    @Modifying
    @Transactional
    @Query(value = "update product set product_status=:status where id=:productId",nativeQuery = true)
    Integer updateStatus(@Param("productId")Long productID,@Param("status")Boolean status);
}
