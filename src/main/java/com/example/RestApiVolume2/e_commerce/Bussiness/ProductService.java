 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.RestApiVolume2.e_commerce.Bussiness;

import com.example.RestApiVolume2.e_commerce.DataAccess.ProductRepository;
import com.example.RestApiVolume2.e_commerce.Entities.Product;
import com.example.RestApiVolume2.e_commerce.Entities.User;
import com.example.RestApiVolume2.e_commerce.Exception.ResourceNorFoundException;
import com.example.RestApiVolume2.e_commerce.Exception.ValidationException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 2005m
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired

    public ProductService( ProductRepository productRepository,@Lazy UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Transactional
    public List<Product> getAll() {
        return productRepository.findAll();

    }

    @Transactional

    public void delete(/*Long userId,*/ Product product) {
       /* if (userService.getById(userId).getRole() != User.UserRole.ADMİN) {
            throw new ValidationException("Insufficient Role");
        }*/

        getById(product.getId());
        productRepository.delete(product);

    }

    @SuppressWarnings("null")
    @Transactional

    public void delete(Long id) {
       /* if (userService.getById(userId).getRole() != User.UserRole.ADMİN) {
            throw new ValidationException("Insufficient Role");
        }*/

       
        productRepository.delete(getById(id));

    }

    
    @Transactional

    public void update(Product product) {
        
//         if (userService.getById(userId).getRole() != User.UserRole.ADMİN) {
//            throw new ValidationException("Insufficient Role");
//        }
        getById(product.getId());

        productRepository.save(product);
    }

    @SuppressWarnings("null")
    private void insert(Product product) {
        productRepository.save(product);
    }

    public Product getById(long id) {

        Optional<Product> item = productRepository.findById(id);

        if (item.isPresent()) {
            return item.get();
        } else {
            throw new ResourceNorFoundException("Product", "id", id);
        }

    }

    @Transactional
    public boolean stockControl(long productId, int quantity) {

        return this.getById(productId).getStock() >= quantity;

    }

    @Transactional
    public void reduceStock(long productId, int quantity) {
        Product product = getById(productId);
        if (product.getStock() < quantity) {
            throw new ValidationException("Insufficient stock for product: " + product.getName());
        }
        product.setStock(product.getStock() - quantity);
        productRepository.save(product);
    }

    @Transactional
    public Product addProduct(Long userId, String name, Double price, int stock) {

        if (userService.getById(userId).getRole() != User.UserRole.ADMİN) {
            throw new ValidationException("Insufficient Role");
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);

        insert(product);
return product;
    }

}
