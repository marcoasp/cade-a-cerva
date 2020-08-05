package br.com.marco.cadeacerva.matcher.domain.sale;

import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SaleSearchCriteriaWrapper {

    private final String userEmail;
    private final double[] location;
    private final double distance;
    private final List<SearchCriteria> interestsCriteria;

    public static SaleSearchCriteriaWrapper from(final UserDTO user) {
        return SaleSearchCriteriaWrapper.builder()
                .userEmail(user.getEmail())
                .location(user.getLocation())
                .distance(user.getArea())
                .interestsCriteria(user.getInterests().stream().map(i -> SearchCriteria.of(user, i)).collect(toList()))
                .build();
    }

    Aggregation createQuery() {
        Criteria criteria = new  Criteria();
        if(!CollectionUtils.isEmpty(this.interestsCriteria)) {
            criteria.orOperator(this.interestsCriteria.stream().map(SearchCriteria::build).collect(toList()).toArray(new Criteria[0]));
        }
        return Aggregation.newAggregation(
                Aggregation.geoNear(NearQuery.near(new Point(location[0],location[1])).maxDistance(new Distance(distance, Metrics.KILOMETERS).getNormalizedValue()).spherical(true), "location"),
                Aggregation.lookup("match", "_id", "saleId", "matches"),
                Aggregation.match(criteria.andOperator(Criteria.where("matches.userEmail").ne(this.userEmail)))
        );
    }
}
