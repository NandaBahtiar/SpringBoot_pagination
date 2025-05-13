package id.nan.belajar_pagination.belajar.pagination.repository;

import id.nan.belajar_pagination.belajar.pagination.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
       Page<Product> findByCategory(String category, Pageable pageable);
}
