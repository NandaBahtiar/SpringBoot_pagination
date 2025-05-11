package id.nan.belajar_pagination.belajar.pagination.service;

import id.nan.belajar_pagination.belajar.pagination.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<Product> getAllProduct(Pageable pageable);
    Product saveProduct(Product product);
}
