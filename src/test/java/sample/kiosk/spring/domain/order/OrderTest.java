package sample.kiosk.spring.domain.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.kiosk.spring.domain.product.Product;
import sample.kiosk.spring.domain.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static sample.kiosk.spring.domain.order.OrderStatus.*;
import static sample.kiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.kiosk.spring.domain.product.ProductType.*;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다")
    @Test
    void calculateTotalPrice() {
        // given
        Product product1 = createProduct("001", 1000);
        Product product2 = createProduct("002", 2000);
        Product product3 = createProduct("003", 3000);

        List<Product> products = List.of(product1, product2, product3);

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getTotalPrice()).isEqualTo(6000);
    }

    @DisplayName("주문 생성 시 주문 상태는 INIT 이다")
    @Test
    void init() {
        // given
        Product product1 = createProduct("001", 1000);
        Product product2 = createProduct("002", 2000);
        Product product3 = createProduct("003", 3000);

        List<Product> products = List.of(product1, product2, product3);

        // when
        Order order = Order.create(products, LocalDateTime.now());

        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(INIT);
    }

    @DisplayName("주문 생성 시 주문 등록 시간을 기록한다")
    @Test
    void registeredDateTime() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.of(2024, 1, 28, 11, 27);

        Product product1 = createProduct("001", 1000);
        Product product2 = createProduct("002", 2000);
        Product product3 = createProduct("003", 3000);
        List<Product> products = List.of(product1, product2, product3);

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);
    }

    private Product createProduct(String productNumber, int price) {
        return Product.builder()
                .type(HANDMADE)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(SELLING)
                .name("메뉴 이름")
                .build();
    }

}