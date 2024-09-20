package org.example.controller;

import org.example.model.Product;
import org.example.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "productCache")
@RestController
@RequestMapping("/api/cache/")
public class CacheController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @CacheEvict(cacheNames = "products", allEntries = true)
    @PostMapping("/product/{name}")
    public Product saveProduct(@PathVariable String name) {
        return productRepository.save(Product.builder().name(name).build());
    }

    @Cacheable(cacheNames = "products", key = "#id", unless = "#result == null")
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "products")
    @GetMapping("/products")
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @CacheEvict(cacheNames = "products", allEntries = true)
    @PutMapping("/product/{id}&{name}")
    public Product updateProduct(@PathVariable long id, @PathVariable String name) {
        Optional<Product> prodOpt = productRepository.findById(id);

        if (prodOpt.isPresent()) {
            return productRepository.updateProductNameById(id, name);
        }

        return productRepository.save(Product.builder().name(name).build());
    }

    @CacheEvict(value = "products", key = "#id")
    @DeleteMapping("/product/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
