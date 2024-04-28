package antigravity.service;

import antigravity.domain.entity.Product;
import antigravity.domain.entity.ProductPrice;
import antigravity.domain.entity.Promotion;
import antigravity.domain.enums.PromotionType;
import antigravity.model.request.ProductInfoRequest;
import antigravity.model.response.ProductAmountResponse;
import antigravity.repository.product.ProductRepository;
import antigravity.repository.promotion.PromotionRepository;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest {

    static ProductRepository productRepository = mock(ProductRepository.class);
    static PromotionRepository promotionRepository = mock(PromotionRepository.class);
    static ProductService productService = new ProductService(productRepository, promotionRepository);
    static ProductInfoRequest request = ProductInfoRequest.builder()
            .productId(1)
            .couponIds(new int[]{1, 2})
            .build();

    @Test
    void getDiscountByAmount_메서드는_할인_정책이_모두_없다면_상품의_원래_가격을_반환한다() {
        // given
        ProductPrice productPrice = new ProductPrice(30000L);
        when(productRepository.findById(any())).thenReturn(Optional.of(Product.builder().id(1L).name("test").productPrice(productPrice).build()));
        when(promotionRepository.findAllAvailablePromotions(any(), any())).thenReturn(List.of());

        // when
        ProductAmountResponse productAmount = productService.getProductAmount(request);

        // then
        assertThat((long) productAmount.getFinalPrice()).isEqualTo(productPrice.getPrice());
    }

    @Test
    void getDiscountByAmount_메서드는_할인_비율과_금액이_원가격에서_계산된만큼_깎인다 () {
        // given
        ProductPrice productPrice = new ProductPrice(215000L);
        when(productRepository.findById(any())).thenReturn(Optional.of(Product.builder().id(1L).name("test").productPrice(productPrice).build()));
        when(promotionRepository.findAllAvailablePromotions(any(), any())).thenReturn(
                List.of(Promotion.builder().promotionType(PromotionType.CODE).discountValue(15L).useStartedAt(ZonedDateTime.now().minusDays(1L)).useEndedAt(ZonedDateTime.now().plusDays(1L)).build(),
                        Promotion.builder().promotionType(PromotionType.COUPON).discountValue(30000L).useStartedAt(ZonedDateTime.now().minusDays(1L)).useEndedAt(ZonedDateTime.now().plusDays(1L)).build()
                ));
        // when
        ProductAmountResponse productAmount = productService.getProductAmount(request);

        // then
        assertThat(productAmount.getFinalPrice()).isEqualTo(152000L);
        assertThat(productAmount.getDiscountPrice()).isEqualTo(62250L);
    }
}