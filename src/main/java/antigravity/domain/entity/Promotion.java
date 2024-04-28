package antigravity.domain.entity;

import antigravity.domain.enums.DiscountType;
import antigravity.domain.enums.PromotionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PromotionType promotionType; //쿠폰 타입 (쿠폰, 코드)
    private String name;
    @Enumerated(EnumType.STRING)
    private DiscountType discountType; // WON : 금액 할인, PERCENT : %할인
    private Long discountValue; // 할인 금액 or 할인 %
    private ZonedDateTime useStartedAt; // 쿠폰 사용가능 시작 기간
    private ZonedDateTime useEndedAt; // 쿠폰 사용가능 종료 기간

    public Long getDiscountPrice(ProductPrice productPrice) {
        switch (promotionType) {
            case COUPON -> {
                return productPrice.getDiscountByAmount(discountValue);
            }
            case CODE -> {
                return productPrice.getDiscountByRate(discountValue);
            }
            default -> throw new IllegalStateException("지원하지 않는 할인 정책입니다.");
        }
    }
}
