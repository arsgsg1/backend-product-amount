package antigravity.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductPrice {
    private Long price;
    @Transient
    public static final Long MIN_PRICE = 0L;

    public ProductPrice(Long price) {
        this.price = price;
    }

    public Long getDiscountByAmount(Long value) {
        return value;
    }

    public Long getDiscountByRate(Long rate) {
        if (rate > 100) {
            throw new IllegalStateException("할인율은 100을 넘을 수 없습니다.");
        }
        return (price * rate) / 100;
    }

    public Long calcPrice(Long totalDiscountAmount) {
        if (price < totalDiscountAmount) {
            return MIN_PRICE;
        }
        return (price - totalDiscountAmount) / 1000 * 1000;
    }
}
