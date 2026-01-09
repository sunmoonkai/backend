package com.demo.market.service;

import com.demo.market.DTO.ProductDTO;
import com.demo.market.entity.Product;
import com.demo.market.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.demo.market.util.ProductNoGenerator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // 配置成一個 spring 的 bean ， @Service 實際上就是一個 @Component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductNoGenerator productNoGenerator;

    public ProductServiceImpl(ProductRepository productRepository, ProductNoGenerator productNoGenerator) {
        this.productRepository = productRepository;
        this.productNoGenerator = productNoGenerator;
    }

    @Override
    public ProductDTO addProduct(ProductDTO product) {
        // productRepository 是 extends CrudRepository<Product,Long>，無法使用 ProductDTO 進行save()
        Product product1 = new Product();
        // 生成商品編號，使用 ProductNoGenerator
        product.setProductNo(productNoGenerator.nextProductNo());
        // 複製屬性
        BeanUtils.copyProperties(product,product1);
        // 儲存
        productRepository.save(product1);
        // 回傳帶 ID 的 DTO
        ProductDTO result = new ProductDTO();
        BeanUtils.copyProperties(product, result); // 這樣 result 就有 ID 和欄位
        return result;
    }

    @Override
    public ProductDTO getProductNo(String productNo) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在!"));

        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        return dto;
    }

    @Override
    public ProductDTO updateProduct(String productNo, ProductDTO dto) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在!"));

        if(dto.getName() != null) product.setName(dto.getName());
        if(dto.getPrice() != null) product.setPrice(dto.getPrice());
        if(dto.getStock() != null) product.setStock(dto.getStock());
        if(dto.getDescription() != null) product.setDescription(dto.getDescription());

        Product saved = productRepository.save(product);

        ProductDTO result = new ProductDTO();
        BeanUtils.copyProperties(saved, result);
        return result;
    }

    @Override
    @Transactional  // 保證刪除原子性
    public void deleteProduct(String productNo) {
        Product product = productRepository.findById(productNo)
                .orElseThrow(() -> new IllegalArgumentException("商品不存在!"));

        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> searchByName(String keyword) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(keyword);
        return products.stream().map(p -> {
            ProductDTO dto = new ProductDTO();
            BeanUtils.copyProperties(p, dto);
            return dto;
        }).toList();
    }

}
