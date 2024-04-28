package antigravity.repository.promotion;

import antigravity.domain.entity.Promotion;

import java.time.ZonedDateTime;
import java.util.List;

public interface PromotionCustomRepository {
    List<Promotion> findAllAvailablePromotions(List<Long> ids, ZonedDateTime now);
}
