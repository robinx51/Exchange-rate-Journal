package ru.journal.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.journal.domain.entity.RateDictEntity;
import ru.journal.domain.entity.RateDictEntity_;

import java.util.List;

@Component
public class RateDictSpecification {

    public static Specification<RateDictEntity> likeRateCodes(List<String> rateCodes) {
        return (root, query, criteriaBuilder) -> {
            if (rateCodes == null || rateCodes.isEmpty()) {
                return null;
            }

            Predicate[] predicates = rateCodes.stream()
                    .filter(StringUtils::hasText)
                    .map(code -> criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(RateDictEntity_.CHAR_CODE)),
                            "%" + code.toLowerCase() + "%"
                    ))
                    .toArray(Predicate[]::new);

            return predicates.length > 0
                    ? criteriaBuilder.or(predicates)
                    : null;
        };
    }
}
