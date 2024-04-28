package antigravity.repository.promotion;

import antigravity.domain.entity.Promotion;
import antigravity.domain.entity.QPromotion;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PromotionCustomRepositoryImpl implements PromotionCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QPromotion promotion = QPromotion.promotion;

    @Override
    public List<Promotion> findAllAvailablePromotions(List<Long> ids, ZonedDateTime now) {
        return jpaQueryFactory.selectFrom(promotion)
                .where(
                        promotion.id.in(ids),
                        promotion.useStartedAt.before(now),
                        promotion.useEndedAt.after(now)
                ).fetch();
    }
}
