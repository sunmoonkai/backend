package com.demo.market.service;

import com.demo.market.DTO.ProductDTO;

import java.util.List;

public interface ProductService {
    /**
     * 新增商品
     * @param product
     * @return
     */
    ProductDTO addProduct(ProductDTO product);

    /**
     * 查找商品編號
     * @param productNo
     * @return
     */
    ProductDTO getProductNo(String productNo);

    /**
     * 修改商品
     * @param productNo
     * @param productDTO
     * @return
     */

    ProductDTO updateProduct(String productNo, ProductDTO productDTO);

    /**
     * 刪除商品
     * @param productNo
     */
    void deleteProduct(String productNo);

    List<ProductDTO> searchByName(String keyword);
}
