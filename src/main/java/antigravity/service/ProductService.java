package antigravity.service;

import antigravity.domain.entity.Product;
import antigravity.domain.entity.ProductPrice;
import antigravity.domain.entity.Promotion;
import antigravity.model.request.ProductInfoRequest;
import antigravity.model.response.ProductAmountResponse;
import antigravity.repository.product.ProductRepository;
import antigravity.repository.promotion.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;
    private final ZoneId KST = ZoneId.of("Asia/Seoul");

    @Transactional(readOnly = true)
    public ProductAmountResponse getProductAmount(ProductInfoRequest request) {
        Product product = productRepository.findById((long) request.getProductId()).orElseThrow(() -> new IllegalArgumentException("해당하는 상품이 존재하지 않습니다."));
        ProductPrice productPrice = product.getProductPrice();
        List<Promotion> allAvailablePromotions = promotionRepository.findAllById(Arrays.stream(request.getCouponIds())
                .mapToLong(Long::valueOf).boxed().collect(Collectors.toList()));

        Long totalDiscount = allAvailablePromotions.stream()
                .map((promotion -> promotion.getDiscountPrice(productPrice)))
                .reduce(0L, Long::sum);

        Long finalPrice = productPrice.calcPrice(totalDiscount);

        return ProductAmountResponse.builder()
                .name(product.getName())
                .originPrice(productPrice.getPrice().intValue())
                .discountPrice(totalDiscount.intValue())
                .finalPrice(finalPrice.intValue())
                .build();
    }
}
