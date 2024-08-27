package vn.hoidanit.laptopshop.service;

import java.util.*;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Optional<Order> fetchOrderById(long id) {
        return this.orderRepository.findById(id);
    }

    public List<Order> fetchOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    public void handleSaveOrder(Order order) {
        this.orderRepository.save(order);
    }

    public List<Order> fetchOrders() {
        return this.orderRepository.findAll();
    }

    public void deleteAOrder(long orderId) {
        Order order = this.orderRepository.findById(orderId).get();
        List<OrderDetail> orderDetails = this.orderDetailRepository.findByOrder(order);
        for (OrderDetail orderDetail : orderDetails) {
            this.orderDetailRepository.delete(orderDetail);
        }
        this.orderRepository.deleteById(orderId);
    }
}
