package com.order.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.stereotype.Service;

import com.order.management.DTO.OrderDTO;
import com.order.management.DTO.OrderResponseDTO;
import com.order.management.entity.Order;
import com.order.management.entity.Product;
import com.order.management.entity.Unit;
import com.order.management.entity.UnitProductMapping;
import com.order.management.entity.User;
import com.order.management.enums.PaymentStatus;
import com.order.management.repository.OrderRepository;
import com.order.management.repository.ProductRepository;
import com.order.management.repository.UnitProductMappingRepository;
import com.order.management.repository.UnitRepository;
import com.order.management.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UnitProductMappingRepository unitProductMappingRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public OrderResponseDTO placeOrder(OrderDTO orderDTO) {
        HashMap<Product, List<Unit>> productUnitMapping = new HashMap<>();
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not Found"));
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        UnitProductMapping unitPordMap = unitProductMappingRepository
                .findFirstByProductIdAndUnitIsAvailableTrue(product.getId())
                .orElseThrow(() -> new RuntimeException("Product Out of Stock"));

        Unit unit = unitPordMap.getUnit();
        unit.setAvailable(false);
        unit = unitRepository.save(unit);
        List<Unit> unitList = new ArrayList<>();
        unitList.add(unit);
        productUnitMapping.put(product, unitList);

        Order order = new Order();
        order.setUser(user);
        order.setAmount(product.getPrice());
        order.setPaymentStatus(PaymentStatus.PENDING);
        Order SavedOrder = orderRepository.save(order);

        UnitProductMapping unitProductMapping = unitProductMappingRepository
                .findByProductIdAndUnitId(product.getId(), unit.getId()).get();
        unitProductMapping.setOrder(SavedOrder);

        product.setStock(product.getStock() - 1);
        productRepository.save(product);

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setUser(user);
        orderResponseDTO.setDatetime(order.getDatetime());
        orderResponseDTO.setAmount(order.getAmount());
        orderResponseDTO.setProductUnitMapping(productUnitMapping);
        return orderResponseDTO;
    }

    public Order makePayment(long id) {

        try {

            Order order = orderRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order not present"));

            if (order.getPaymentStatus() == PaymentStatus.DONE) {
                throw new RuntimeException("Payment already completed");
            }

            order.setPaymentStatus(PaymentStatus.DONE);

            HashMap<Product, List<Unit>> productUnitMapping = new HashMap<>();

            List<UnitProductMapping> unitProductMappings = order.getUnitProductMappings();

            for (UnitProductMapping upm : unitProductMappings) {

                Product prd = upm.getProduct();

                Unit unit = upm.getUnit();

                List<Unit> unitList = productUnitMapping.getOrDefault(
                        prd,
                        new ArrayList<>());

                unitList.add(unit);

                productUnitMapping.put(prd, unitList);
            }

            OrderResponseDTO orderResponseDTO = new OrderResponseDTO();

            orderResponseDTO.setId(order.getId());
            orderResponseDTO.setUser(order.getUser());
            orderResponseDTO.setDatetime(order.getDatetime());
            orderResponseDTO.setAmount(order.getAmount());
            orderResponseDTO.setProductUnitMapping(
                    productUnitMapping);

            return orderRepository.save(order);

        } catch (Exception e) {

            throw new RuntimeException(e.getMessage());
        }
    }
}
