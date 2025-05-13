package id.nan.belajar_pagination.belajar.pagination.service;

import id.nan.belajar_pagination.belajar.pagination.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAllProduct(String category, double price, Pageable pageable);
    Page<Product> findByCategory(Pageable pageable, String category);
    Product saveProduct(Product product);

}
