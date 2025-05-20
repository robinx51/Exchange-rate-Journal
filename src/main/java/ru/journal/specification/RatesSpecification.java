package ru.journal.specification;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.journal.model.domain.entity.*;
import ru.journal.model.domain.entity.CountryEntity;
import ru.journal.model.domain.entity.RateDictEntity;
import ru.journal.model.domain.entity.RateEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

            Subquery<Long> countrySubquery = Objects.requireNonNull(query).subquery(Long.class);
            Root<CountryEntity> countryRoot = countrySubquery.from(CountryEntity.class);

            Predicate countryPredicate = CountrySpecification.likeCountryNames(countryNames)
                    .toPredicate(countryRoot, query, criteriaBuilder);

            countrySubquery.select(countryRoot.get(CountryEntity_.ID))
                    .where(countryPredicate);

            return root.get(RateEntity_.COUNTRY)
                    .get(CountryEntity_.ID)
                    .in(countrySubquery);
        };
    }

    public static Specification<RateEntity> likeRateCodes(List<String> rateCodes) {
        return (root, query, criteriaBuilder) -> {
            if (rateCodes == null || rateCodes.isEmpty()) {
                return null;
            }

            Subquery<Long> rateSubquery = Objects.requireNonNull(query).subquery(Long.class);
            Root<RateDictEntity> rateRoot = rateSubquery.from(RateDictEntity.class);

            Predicate ratePredicate = RateDictSpecification.likeRateCodes(rateCodes)
                    .toPredicate(rateRoot, query, criteriaBuilder);

            rateSubquery.select(rateRoot.get(RateDictEntity_.ID))
                    .where(ratePredicate);

            return root.get(RateEntity_.RATE_DICT)
                    .get(RateEntity_.ID)
                    .in(rateSubquery);
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
