package antigravity.service;

import antigravity.domain.entity.ProductPrice;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductPriceTest {
    @Test
    void 할인_금액이_원가격보다_크다면_최소_금액을_반환한다() {
        // given
        ProductPrice price = new ProductPrice(30000L);

        // when
        Long finalPrice = price.calcPrice(40000L);

        // then
        assertThat(finalPrice).isEqualTo(ProductPrice.MIN_PRICE);
    }
    @Test
    void 가격은_1000원_단위로_절삭한다() {
        ProductPrice price = new ProductPrice(30500L);
        assertThat(price.calcPrice(0L)).isEqualTo(30000L);
    }

    @Test
    void getDiscountByAmount_메서드는_금액_할인가격을_반환한다() {
        // given
        ProductPrice price = new ProductPrice(30000L);
        Long discountValue = 10000L;

        // when
        Long discountByAmount = price.getDiscountByAmount(discountValue);

        // then
        assertThat(discountByAmount).isEqualTo(discountValue);
    }

    @Test
    void getDiscountByRate_메서드는_비율_할인가격을_반환한다() {
        // given
        ProductPrice price = new ProductPrice(30000L);
        Long discountValue = 30L;

        // when
        Long expect = price.getDiscountByRate(discountValue);

        // then
        assertThat(expect).isEqualTo((price.getPrice() * discountValue) / 100);
    }
}
