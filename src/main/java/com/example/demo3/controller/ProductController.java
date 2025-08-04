package com.example.demo3.controller;

import com.example.demo3.entity.Product;
import com.example.demo3.repository.ProductRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
  private final ProductRepository productRepository;
  @GetMapping
  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    return productRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productRepository.save(product);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Product> updateProductName(@PathVariable Long id, @RequestBody Product product) {
    return productRepository.findById(id)
        .map(p -> {
          p.setName(product.getName());
          return ResponseEntity.ok(productRepository.save(p));
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
    if (productRepository.existsById(id)) {
      productRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
