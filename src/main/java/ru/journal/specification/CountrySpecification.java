package ru.journal.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.journal.model.domain.entity.CountryEntity;
import ru.journal.model.domain.entity.CountryEntity_;

import java.util.List;

@Component
public class CountrySpecification {

    public static Specification<CountryEntity> likeCountryNames(List<String> countryNames) {
        return (root, query, criteriaBuilder) -> {
            if (countryNames == null || countryNames.isEmpty()) {
                return null;
            }

            Predicate[] predicates = countryNames.stream()
                    .filter(StringUtils::hasText)
                    .map(code -> criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(CountryEntity_.name)),
                            "%" + code.toLowerCase() + "%"
                    ))
                    .toArray(Predicate[]::new);

            return predicates.length > 0
                    ? criteriaBuilder.or(predicates)
                    : null;
        };
    }
}
