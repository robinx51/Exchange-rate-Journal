package ru.journal.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.journal.model.domain.entity.*;
import ru.journal.model.domain.entity.CountryEntity;
import ru.journal.model.domain.entity.RateDictEntity;
import ru.journal.model.domain.entity.RateEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class RatesSpecification {
    public static Specification<RateEntity> likeRateDates(List<LocalDate> rateDates) {
        return (((root, query, criteriaBuilder) -> {
            if (rateDates == null || rateDates.isEmpty()) {
                return null;
            }

            return root.get(RateEntity_.RATE_DATE).in(rateDates);
        }));
    }

    public static Specification<RateEntity> likeCountries(List<String> countryNames) {
        return (root, query, criteriaBuilder) -> {
            if (countryNames == null || countryNames.isEmpty()) {
                return null;
            }

            Join<RateEntity, CountryEntity> join = root.join(RateEntity_.COUNTRY);
            List<Predicate> predicates = new ArrayList<>();
            for (String countryName : countryNames) {
                if (StringUtils.hasText(countryName)) {
                    predicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(join.get(CountryEntity_.NAME)),
                                    "%" + countryName.toLowerCase() + "%"
                            )
                    );
                }
            }
            return predicates.isEmpty()
                    ? null
                    : criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<RateEntity> likeRateCodes(List<String> rateCodes) {
        return (root, query, criteriaBuilder) -> {
            if (rateCodes == null || rateCodes.isEmpty()) {
                return null;
            }

            Join<RateEntity, RateDictEntity> join = root.join(RateEntity_.RATE_DICT);
            List<Predicate> predicates = new ArrayList<>();
            for (String code : rateCodes) {
                if (StringUtils.hasText(code)) {
                    predicates.add(
                            criteriaBuilder.like(
                                    criteriaBuilder.lower(join.get(RateDictEntity_.CHAR_CODE)),
                                    "%" + code.toLowerCase() + "%"
                            )
                    );
                }
            }

            return predicates.isEmpty()
                    ? null
                    : criteriaBuilder.or(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<RateEntity> likeNominal(List<Long> nominals) {
        return ((root, query, criteriaBuilder) -> {
            if (nominals == null || nominals.isEmpty()) {
                return null;
            }
            return root.get(RateEntity_.NOMINAL).in(nominals);
        });
    }

    public static Specification<RateEntity> likeValues(List<BigDecimal> values) {
        return ((root, query, criteriaBuilder) -> {
            if (values == null || values.isEmpty()) {
                return null;
            }
            return root.get(RateEntity_.VALUE).in(values);
        });
    }
}
