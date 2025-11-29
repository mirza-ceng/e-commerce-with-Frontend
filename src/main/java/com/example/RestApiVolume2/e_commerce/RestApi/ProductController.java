/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.RestApi;

import com.example.RestApiVolume2.e_commerce.Bussiness.ProductService;
import com.example.RestApiVolume2.e_commerce.Entities.Product;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author 2005m
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getById(id);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        productService.delete(id);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestParam Long userId, @RequestParam String name, @RequestParam Double price, @RequestParam Integer stock) {

        return productService.addProduct(userId, name, price, stock);

    }

    @GetMapping
    public List<Product> getAll() {

        return productService.getAll();
    }
}
