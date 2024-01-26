package sample.kiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static sample.kiosk.spring.domain.product.ProductSellingStatus.*;

@ActiveProfiles("test")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("1")
                .name("아메리카노")
                .sellingStatus(SELLING)
                .build();

        Product product2 = Product.builder()
                .productNumber("2")
                .name("라떼")
                .sellingStatus(HOLD)
                .build();

        Product product3 = Product.builder()
                .productNumber("3")
                .name("쌍화탕")
                .sellingStatus(STOP_SELLING)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        assertThat(products.get(0).getSellingStatus()).isEqualTo(SELLING);

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("1", "아메리카노", SELLING),
                        tuple("2", "라떼", HOLD)
                );
    }

}