package com.example.javamararathon1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class JavaMararathon1Application {

    public static void main(String[] args) {
        SpringApplication.run(JavaMararathon1Application.class, args);
    }

}

@RestController
@RequestMapping("/product")
class ProductController{
    final ProductService productService;


    ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public Iterable<Product> findAllProduct(){
        return productService.findAllOfProduct();
    }
}

record Product(@Id int id, String name) {
}

interface ProductRepository extends CrudRepository<Product, Integer> {
}
@Service
final class  ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Iterable<Product> findAllOfProduct(){
        return productRepository.findAll();
    }
}
