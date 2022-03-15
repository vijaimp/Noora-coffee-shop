package com.example.nooracoffeeshop.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.example.nooracoffeeshop.Repositories.DepartmentRepository;
import com.example.nooracoffeeshop.Repositories.ImageRepository;
import com.example.nooracoffeeshop.Repositories.ManufacturerRepository;
import com.example.nooracoffeeshop.Repositories.ProductRepository;
import com.example.nooracoffeeshop.Repositories.SupplierRepository;
import com.example.nooracoffeeshop.model.Image;
import com.example.nooracoffeeshop.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private ImageRepository imageRepository;

    public Page<Product> topSellers(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getAllCoffeeMachines(List<Long> coffeemachines) {
        List<Product> products = productRepository.findByDepartment_idIn(coffeemachines);
        return products;
    }

    public List<Product> getAllConsumerProducts(List<Long> consumerProducts) {
        List<Product> products = productRepository.findByDepartment_idIn(consumerProducts);
        return products;
    }

    public List<Product> getAllCoffees(List<Long> coffees) {
        List<Product> products = productRepository.findByDepartment_idIn(coffees);
        return products;
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.getById(id);
    }

    public void addProduct(Long manufacturerID, String productName, String productDescription, Long departmentID,
            Long supplierID, Double productPrice, String name, String description, MultipartFile image,
            Long productsSold) throws IOException {

        Product product = new Product();
        Image image1 = new Image();
        image1.setName(name);
        image1.setDescription(description);
        image1.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
        product.setName(productName);
        product.setPrice(productPrice);
        product.setDescription(productDescription);
        product.setDepartment(departmentRepository.getById(manufacturerID));
        product.setManufacturer(manufacturerRepository.getById(manufacturerID));
        product.setSupplier(supplierRepository.getById(supplierID));
        product.setProductSold(productsSold);
        product.setImages(Arrays.asList(image1));
        imageRepository.save(image1);
        productRepository.save(product);
    }

    public void updateProduct(Long manufacturerID, String productName, String productDescription, Long departmentID,
            Long supplierID, Double productPrice, String name, String description, MultipartFile image,
            Long productsSold, Long id) throws IOException {
        Product existingProduct = productRepository.getById(id);

        existingProduct.setDepartment(departmentRepository.getById(manufacturerID));
        existingProduct.setManufacturer(manufacturerRepository.getById(manufacturerID));
        existingProduct.setSupplier(supplierRepository.getById(supplierID));
        existingProduct.setName(productName);
        existingProduct.setPrice(productPrice);
        existingProduct.setDescription(productDescription);
        existingProduct.setProductSold(productsSold);

        productRepository.save(existingProduct);

    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
