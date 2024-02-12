package sample.kiosk.spring.api.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.kiosk.spring.api.controller.product.dto.request.ProductCreateRequest;
import sample.kiosk.spring.api.service.product.response.ProductResponse;
import sample.kiosk.spring.domain.product.Product;
import sample.kiosk.spring.domain.product.ProductRepository;
import sample.kiosk.spring.domain.product.ProductSellingStatus;

import java.util.List;
import java.util.stream.Collectors;

// readOnly true => 읽기 전용 cud 동작X, jpa: cud 스냅샷 저장, 변경감지 등을 안해서 성능향상
// cqrs - 커맨드/ 리드 분리 가능해짐
// 쿼리용 서비스, 커맨드용 서비스 분리
// db엔드포인트를 구분함으로써 장애격리 ex) 커맨드는 master db, 쿼리는 slave db 사용, 각자 다른 db 사용해서 장애 안됨
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateRequest request) {
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProductNumber();
        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }

    public List<ProductResponse> getSellingProducts() {

        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
