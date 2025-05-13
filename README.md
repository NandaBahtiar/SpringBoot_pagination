# Java SpringBoot Pagination


 lorem
 SpringBoot version 3.4.5   
### Dependency
- Spring Web (untuk membuat REST API)
- Spring Data JPA (untuk interaksi database)
- PostgreSQL Driver
- Lombok (opsional, untuk mengurangi boilerplate code)
### Konfigurasi Database PostgreSQL
` application.properties`
```bash
# PostgreSQL DataSource Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/pagination_db
spring.datasource.username=postgres # Ganti dengan username PostgreSQL Anda
spring.datasource.password=yourpassword # Ganti dengan password PostgreSQL Anda
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update # update: membuat/memperbarui tabel otomatis. 'create-drop' untuk development, 'validate' atau 'none' untuk production
spring.jpa.show-sql=true # Menampilkan query SQL di console (baik untuk debugging)
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true # Format SQL agar lebih mudah dibaca
```

## Buat Entity
```bash
@Entity
@Table(name = "products")
@Data // Lombok: @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Cocok untuk auto-increment di PostgreSQL
    private Long id;

    private String name;
    private double price;
    private String category;
}
```
## Buat Repository
``` bash
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // JpaRepository sudah menyediakan method findAll(Pageable pageable)
    // Kita tidak perlu menambahkan method khusus untuk pagination dasar.
    // Jika perlu filtering + pagination, bisa ditambahkan di sini, contoh:
    // Page<Product> findByCategory(String category, Pageable pageable);
}
```
## Buat Service
* interface `ProductService`
```bash
public interface ProductService {
    Page<Product> getAllProduct(Pageable pageable);
    Product saveProduct(Product product);
}
```

* implemen `ProductServiceImpl`
```bash
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
```
## Buat Controller
```bash

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
```
## menambahkan spesifikasi pagination berdasarkan category
* tambahkan di repository
```bash
       Page<Product> findByCategory(String category, Pageable pageable);
```
* tambahkan di service
```bash
  @Override
    public Page<Product> findByCategory(Pageable pageable, String category) {
        return productRepository.findByCategory(category,pageable);
    }
```
* edit di nagian controller
```bash
 @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort,
            @RequestParam(defaultValue = "") String category
    ){
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        String sortField= sort[0];
        Sort sortBy = Sort.by(direction,sortField);
        Pageable pageable = PageRequest.of(page,size,sortBy);

        if (category.isEmpty()){
            Page<Product> productsPage = productService.getAllProduct(pageable);
            return ResponseEntity.ok(productsPage);
        }else{
            Page<Product> productsPage = productService.findByCategory(pageable, category);
            return ResponseEntity.ok(productsPage);
        }
    }
```
## Postman
[link](https://github.com/NandaBahtiar/SpringBoot_pagination/blob/master/pagination.postman_collection.json)
