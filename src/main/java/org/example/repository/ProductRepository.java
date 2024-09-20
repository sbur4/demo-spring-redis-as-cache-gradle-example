package org.example.repository;

import org.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("UPDATE Product p SET p.name = :name WHERE p.id = :id")
    Product updateProductNameById(Long id, String name);
}