package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final RoleRepository roleRepository;

    public ProductService(ProductRepository productRepository, RoleRepository roleRepository) {
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);// trả kết quả thành công từ database về

    }

    public void deleteAProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }
}
