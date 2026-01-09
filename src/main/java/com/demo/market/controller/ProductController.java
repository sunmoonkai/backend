package com.demo.market.controller;

import com.demo.market.util.ApiResponse;
import com.demo.market.DTO.ProductDTO;
import com.demo.market.service.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController = @Controller + @ResponseBody
// 方法回傳物件
// Spring Boot 會自動 序列化成 JSON
// 前端收到就是 JSON 格式
@RestController
@RequestMapping("/product")
public class ProductController {

//    原始注入使用 Autowired 改成建構子注入

//    @Autowired
//    IProductService productService;

    private final ProductService productService;

    // 建構子注入
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //add method: post
    @PostMapping()
    public ApiResponse<ProductDTO> addProduct(@Validated @RequestBody ProductDTO product){
        ProductDTO created = productService.addProduct(product);
        return new ApiResponse<>(true, "商品新增成功", created);
    }

    //query
    @GetMapping("/{productNo}")
    public ApiResponse<ProductDTO> queryProduct(@PathVariable String productNo){
        ProductDTO product = productService.getProductNo(productNo);
        return new ApiResponse<>(true, "商品查詢成功", product);
    }

    //update
    @PutMapping("/{productNo}")
    public ApiResponse<ProductDTO> updateProduct(
            @PathVariable String productNo,
            @Validated @RequestBody ProductDTO productDTO) {

        ProductDTO updated = productService.updateProduct(productNo, productDTO);

        return new ApiResponse<>(true, "商品更新成功", updated);
    }

    //delete
    @DeleteMapping("/{productNo}")
    public ApiResponse<Void> deleteProduct(@PathVariable String productNo) {
        productService.deleteProduct(productNo);
        return new ApiResponse<>(true, "商品刪除成功", null);
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductDTO>> search(@RequestParam String keyword) {
        return new ApiResponse<>(true, "查詢成功", productService.searchByName(keyword));
    }

}
