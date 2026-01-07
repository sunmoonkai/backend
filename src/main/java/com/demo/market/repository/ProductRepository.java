package com.demo.market.repository;

import com.demo.market.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

    // 關鍵字搜尋，忽略大小寫
    List<Product> findByNameContainingIgnoreCase(String keyword);
}

