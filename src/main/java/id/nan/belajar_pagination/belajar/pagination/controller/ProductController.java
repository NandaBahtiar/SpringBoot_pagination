package id.nan.belajar_pagination.belajar.pagination.controller;

import id.nan.belajar_pagination.belajar.pagination.entity.Product;
import id.nan.belajar_pagination.belajar.pagination.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ){
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        String sortField= sort[0];
        Sort sortBy = Sort.by(direction,sortField);

        Pageable pageable = PageRequest.of(page,size,sortBy);

        Page<Product> productsPage = productService.getAllProduct(pageable);
        return ResponseEntity.ok(productsPage);
    }
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.saveProduct(product));

    }
}
