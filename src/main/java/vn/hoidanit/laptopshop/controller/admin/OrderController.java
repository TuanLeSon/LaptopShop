package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import java.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/admin/order")
    public String getDashboard(Model model,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            } else {
            }
        } catch (Exception e) {
        }
        Pageable pageable = PageRequest.of(page - 1, 2);
        Page<Order> orders = this.orderService.fetchAllOrders(pageable);
        List<Order> listOrders = orders.getContent();
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("orders", listOrders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Order order = this.orderService.fetchOrderById(id).get();
        List<OrderDetail> orderDetails = order.getOrderDetails();
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/detail";
    }

    @GetMapping("/admin/order/update/{id}") // GET
    public String getUpdateOrderPage(Model model, @PathVariable long id) {

        model.addAttribute("id", id);
        Order currentOrder = this.orderService.fetchOrderById(id).get();
        model.addAttribute("newOrder", currentOrder);
        return "admin/order/update";
    }

    @PostMapping(value = "/admin/order/update")
    public String postUpdateOrder(Model model, @ModelAttribute("newOrder") Order order) {
        Order currentOrder = this.orderService.fetchOrderById(order.getId()).get();
        if (currentOrder != null) {
            currentOrder.setStatus(order.getStatus());
            this.orderService.handleSaveOrder(currentOrder);
        }

        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/delete/{id}") // GET
    public String getDeleteOrderPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newOrder", new Order());
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete") // GET
    public String postDeleteOrderPage(Model model, @ModelAttribute("newOrder") Order order) {

        this.orderService.deleteAOrder(order.getId());
        return "redirect:/admin/order";
    }

}
