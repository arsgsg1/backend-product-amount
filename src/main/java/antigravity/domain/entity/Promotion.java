package antigravity.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String promotionType; //쿠폰 타입 (쿠폰, 코드)
    private String name;
    private String discountType; // WON : 금액 할인, PERCENT : %할인
    private Long discountValue; // 할인 금액 or 할인 %
    private ZonedDateTime useStartedAt; // 쿠폰 사용가능 시작 기간
    private ZonedDateTime useEndedAt; // 쿠폰 사용가능 종료 기간
}
