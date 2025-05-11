package id.nan.belajar_pagination.belajar.pagination.service.impl;

import id.nan.belajar_pagination.belajar.pagination.entity.Product;
import id.nan.belajar_pagination.belajar.pagination.repository.ProductRepository;
import id.nan.belajar_pagination.belajar.pagination.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product saveProduct(Product product) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productList.add(Product.builder()
                    .name(product.getName()+i)
                    .price(2000.0)
                    .category("test")
                    .build());

        }
        productRepository.saveAll(productList);
        return product;
    }
}
