package id.nan.belajar_pagination.belajar.pagination.service.impl;

import id.nan.belajar_pagination.belajar.pagination.entity.Product;
import id.nan.belajar_pagination.belajar.pagination.repository.ProductRepository;
import id.nan.belajar_pagination.belajar.pagination.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public Page<Product> getAllProduct(String category, double price, Pageable pageable) {
        Specification < Product > specification = spesification.filterByCategory(category,price);
        return productRepository.findAll(specification,pageable);
    }

    @Override
    public Page<Product> findByCategory(Pageable pageable, String category) {
        return productRepository.findByCategory(category,pageable);
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
class spesification {
    public static Specification <Product> filterByCategory(String category,double price) {
        return (root, query, cb) ->{
                if (category != null && !category.isEmpty()) {
                    return   cb.equal(root.get("category"), category);
                }
            System.out.println("ini dia+++++++++++++++++++++++ "+price);
                 if (price != 0.0){
                    return cb.greaterThan(root.get("price"), price);
                }


//            cb.conjunction() → Menghasilkan ekspresi true (tanpa filter tambahan).
//            cb.disjunction() → Menghasilkan ekspresi false (untuk kondisi tidak mungkin).

                return cb.conjunction();

    };
    }
}

