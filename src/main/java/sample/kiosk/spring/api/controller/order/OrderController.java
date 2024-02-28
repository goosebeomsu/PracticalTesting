package sample.kiosk.spring.api.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.kiosk.spring.api.ApiResponse;
import sample.kiosk.spring.api.service.order.OrderService;
import sample.kiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.kiosk.spring.api.service.order.response.OrderResponse;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("v1/orders/new")
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderCreateRequest request) {
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), LocalDateTime.now()));
    }
}
