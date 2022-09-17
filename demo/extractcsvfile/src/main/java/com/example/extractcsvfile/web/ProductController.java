package com.example.extractcsvfile.web;

import com.example.extractcsvfile.model.Product;
import com.example.extractcsvfile.repo.ProductRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {

    final ProductRepository<Product> productRepository;

    Function<Product, Map<String, Object>> processor = p -> {
        System.out.println("processor");
        return Map.of(String.valueOf(p.getId()),p);
    };
    Consumer<List<Map<String, Object>>> consumer = processRecord -> {

        var directory  = new File("");
        if(!directory.exists()){
            directory.mkdir();
        }
        var rows = processRecord.stream().collect(Collectors.toList());
        
        for(Map<String,Object> row:rows){
            var csvFile = buildCsvLine(row);

        }
        
        System.out.println("consumer");
        System.out.println(processRecord);
    };

    private File buildCsvLine(Map<String, Object> row) {
        return null
    }

    @GetMapping("/hello")
    public String extract() {


        productRepository.extractRecords("product", processor, consumer);

        return "started";
    }
}
