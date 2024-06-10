package com.example.demo.repositories;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product product);
    // insert into product values ();
    Product findByTitle(String title);
    // select * from product where title = {};
    Product findByDescription(String description);
}
