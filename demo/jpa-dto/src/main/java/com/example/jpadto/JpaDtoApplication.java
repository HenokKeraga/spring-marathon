package com.example.jpadto;

import com.example.jpadto.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootApplication
public class JpaDtoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaDtoApplication.class, args);
    }

}


@Entity
@Table(name = "product")
@Data
class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int price;
}


@Repository
interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
      SELECT p FROM Product p
      """)
    List<Product> findAll(); // taking entities and putting them in the JPA context

    @Query("""
              SELECT new com.example.jpadto.dto.ProductDTO(p.id,p.name) FROM  Product  p where p.id=2
            """)
     List<ProductDTO> findAllDTO();
}
@RestController
@RequestMapping("/products")
@Transactional
class ProductController {
    final ProductRepository productRepository;

    ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllProduct(){
        var list = productRepository.findAll();
        list.forEach(p ->p.setPrice(1000));
        return list;
    }
    @GetMapping("/dto")
    public List<ProductDTO> getAllProductDTO(){
        return productRepository.findAllDTO();
    }

    @GetMapping("/exe")
    public List<ProductDTO> getAllProductExe(){
        throw  new ApiRequestException("exception");
    }

}

class ApiRequestException extends RuntimeException {
    public ApiRequestException(String message) {
        super(message);
    }
}
@Builder
@Data
class ApiException{
    String message;
    String status;

}
@ControllerAdvice
class ProductExceptionHandler{
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handleException(ApiRequestException e){
  ApiException apiException= ApiException.builder()
          .message(e.getMessage())
          .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())

          .build();

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiException);
    }
}
