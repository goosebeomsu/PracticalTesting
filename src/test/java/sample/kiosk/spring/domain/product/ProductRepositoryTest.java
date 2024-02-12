package sample.kiosk.spring.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
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
        Product product1 = createProduct("1", "아메리카노", SELLING, 4000);
        Product product2 = createProduct("2", "라떼", HOLD, 4000);
        Product product3 = createProduct("3", "쌍화탕", STOP_SELLING, 4000);
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

    @DisplayName("상품번호 리스트로 상품들을 조회한다")
    @Test
    void findByProductNumberIn() {
        // given
        Product product1 = createProduct("1", "아메리카노", SELLING, 4000);
        Product product2 = createProduct("2", "라떼", HOLD, 4000);
        Product product3 = createProduct("3", "쌍화탕", STOP_SELLING, 4000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("1", "2"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name")
                .containsExactlyInAnyOrder(
                        tuple("1", "아메리카노"),
                        tuple("2", "라떼")
                );
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다")
    @Test
    void findLatestProductNumber() {
        // given
        String targetProductNumber = "003";

        Product product1 = createProduct("001", "아메리카노", SELLING, 4000);
        Product product2 = createProduct("002", "라떼", HOLD, 3000);
        Product product3 = createProduct(targetProductNumber, "쌍화탕", STOP_SELLING, 4000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String result = productRepository.findLatestProductNumber();

        // then
        assertThat(result).isEqualTo(targetProductNumber);
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올때, 상품이 하나도 없는 경우에는 null 을 반환한다")
    @Test
    void findLatestProductNumberWhenProductIsEmpty() {
        // when
        String result = productRepository.findLatestProductNumber();

        // then
        assertThat(result).isNull();
    }

    private Product createProduct(String productNumber, String name, ProductSellingStatus sellingStatus, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .name(name)
                .sellingStatus(sellingStatus)
                .price(price)
                .build();
    }

}