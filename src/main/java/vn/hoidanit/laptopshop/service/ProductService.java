package vn.hoidanit.laptopshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository,
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);// trả kết quả thành công từ database về

    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {

        // lưu cart_detail
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check user đã có cart chưa, nếu chưa -> tạo mới
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }
            // save cart detail
            // tìm product by id
            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();
                // check sản phẩm đã từng được thêm vào giỏ hàng trước đây hay chưa ?
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
                if (oldDetail == null) {
                    CartDetail cd = new CartDetail();
                    cd.setCart(cart);
                    cd.setProduct(realProduct);
                    cd.setPrice(realProduct.getPrice());
                    cd.setQuantity(1);
                    this.cartDetailRepository.save(cd);
                    // update cart sum
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

    public void handleDeleteCartDetail(long cartDetailId, HttpSession session) {

        CartDetail currentCartDetail = this.cartDetailRepository.findById(cartDetailId).get();
        if (currentCartDetail != null) {
            Cart cart = currentCartDetail.getCart();
            this.cartDetailRepository.deleteById(cartDetailId);
            int s = cart.getSum();
            if (s > 1) {
                cart.setSum(s - 1);
                this.cartRepository.save(cart);
                session.setAttribute("sum", s);
            } else {
                this.cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }

        }

    }

    public void deleteAProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }
}
