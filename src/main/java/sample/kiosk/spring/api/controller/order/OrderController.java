package sample.kiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.kiosk.spring.api.service.order.OrderService;
import sample.kiosk.spring.api.service.order.request.OrderCreateRequest;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("v1/orders/new")
    public void createOrder(@RequestBody OrderCreateRequest request) {

        orderService.createOrder(request, LocalDateTime.now());
    }
}
