package com.shoppingmate.service;

import com.shoppingmate.exception.ProductException;
import com.shoppingmate.model.Category;
import com.shoppingmate.model.Product;
import com.shoppingmate.repository.CategoryRepository;
import com.shoppingmate.repository.ProductRepository;
import com.shoppingmate.request.CreateProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    private UserService userService;
    private CategoryRepository categoryRepository;

    public ProductServiceImplementation(ProductRepository productRepository, UserService userService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(CreateProductRequest request) {
        Category topLevel = categoryRepository.findByName(request.getTopLevelCategory());
        if (topLevel == null) {
            Category topLevelCategory = new Category();
            topLevelCategory.setName(request.getTopLevelCategory());
            topLevelCategory.setLevel(1);

            topLevel = categoryRepository.save(topLevelCategory);
        }
        Category secondLevel = categoryRepository.findByNameAndParent(request.getTopLevelCategory(), topLevel.getName());
        if (secondLevel == null) {
            Category secondLevelCategory = new Category();
            secondLevelCategory.setName(request.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);

            secondLevel = categoryRepository.save(secondLevelCategory);
        }
        Category thirdLevel = categoryRepository.findByNameAndParent(request.getThirdLevelCategory(), secondLevel.getName());
        if (thirdLevel == null) {
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(request.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setColor(request.getColor());
        product.setDescription(request.getDescription());
        product.setDiscountedPrice(request.getDiscountedPrice());
        product.setDiscountPercent(request.getDiscountPercent());
        product.setImageUrl(request.getImageUrl());
        product.setBrand(request.getBrand());
        product.setPrice(request.getPrice());
        product.setPrice(request.getPrice());
        product.setSizes(request.getSize());
        product.setQuantity(request.getQuantity());
        product.setCategory(thirdLevel);
        product.setCreatedAt(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product Deleted Successfully !";
    }

    @Override
    public Product updateProduct(Long productId, Product request) throws ProductException {
        Product product = findProductById(productId);
        if (request.getQuantity() != 0) {
            product.setQuantity(request.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> optional = productRepository.findById(productId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new ProductException("Product Not Found With Id :" + productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) {

        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes,
                                       Integer minPrice, Integer maxPrice, Integer minDiscount,
                                       String sort, String stock, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> productList = productRepository
                .filterProducts(category, minPrice, maxPrice, minDiscount, sort);
        if (!colors.isEmpty()) {
            productList = productList.stream().filter(p -> colors.stream()
                    .anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }
        if (stock != null) {
            if (stock.equals("in_stock")) {
                productList = productList.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                productList = productList.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), productList.size());

        List<Product> pageContent = productList.subList(startIndex, endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, productList.size());
        return filteredProducts;
        
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }


}
