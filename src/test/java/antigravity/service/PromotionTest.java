package antigravity.service;

import antigravity.domain.entity.ProductPrice;
import antigravity.domain.entity.Promotion;
import antigravity.domain.enums.PromotionType;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionTest {
    @Test
    void isAvailable_메서드는_프로모션은_시작과_종료기간_사이의_시간이_들어오면_true를_반환한다 () {
        ZonedDateTime useAt = ZonedDateTime.now();
        Promotion promotion = Promotion.builder().useStartedAt(useAt.minusDays(1)).useEndedAt(useAt.plusDays(1)).build();
        assertThat(promotion.isAvailable(useAt)).isTrue();
    }

    @Test
    void isAvailable_메서드는_프로모션은_시작과_종료기간_외의_시간이_들어오면_false를_반환한다 () {
        ZonedDateTime useAt = ZonedDateTime.now();
        Promotion promotion = Promotion.builder().useStartedAt(useAt.minusDays(1)).useEndedAt(useAt.plusDays(1)).build();
        assertThat(promotion.isAvailable(useAt.minusDays(3))).isFalse();
        assertThat(promotion.isAvailable(useAt.plusDays(3))).isFalse();
    }

    @Test
    void getDiscountPrice_메서드는_프로모션_타입이_쿠폰이라면_금액_계산된_결과를_반환한다() {
        long price = 30000L;
        long discount = 10000L;
        Promotion promotion = Promotion.builder().discountValue(discount).promotionType(PromotionType.COUPON).build();
        assertThat(promotion.getDiscountPrice(new ProductPrice(price))).isEqualTo(discount);
    }

    @Test
    void getDiscountPrice_메서드는_프로모션_타입이_코드라면_비율_계산된_결과를_반환한다() {
        long price = 30000L;
        long discount = 10L;
        Promotion promotion = Promotion.builder().discountValue(discount).promotionType(PromotionType.CODE).build();
        assertThat(promotion.getDiscountPrice(new ProductPrice(price))).isEqualTo(price / discount);
    }
}
