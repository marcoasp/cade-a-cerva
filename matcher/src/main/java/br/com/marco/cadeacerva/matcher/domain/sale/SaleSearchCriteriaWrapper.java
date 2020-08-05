package br.com.marco.cadeacerva.matcher.domain.sale;

import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

public class SaleSearchCriteriaWrapper {

    private final String userEmail;
    private final double[] location;
    private final double distance;
    private final List<SearchCriteria> criterias = new ArrayList<>();

    public SaleSearchCriteriaWrapper(final String userEmail, double[] location, double distance, final List<SearchCriteria> criterias) {
        this.userEmail = userEmail;
        this.location = location;
        this.distance = distance;
        this.criterias.addAll(criterias);
    }

    public static SaleSearchCriteriaWrapper from(final UserDTO user) {
        return new SaleSearchCriteriaWrapper(user.getEmail(), user.getLocation(), user.getArea(), user.getInterests().stream().map(i -> SearchCriteria.of(user, i)).collect(toList()));
    }

    public Aggregation createQuery() {
        List<Criteria> criterias = this.criterias.stream().map(SearchCriteria::build).collect(toList());
        Criteria criteria = new Criteria();
        if(!CollectionUtils.isEmpty(criterias)) {
            criteria.orOperator(criterias.toArray(new Criteria[criterias.size()]));
        }
        return Aggregation.newAggregation(
                Aggregation.geoNear(NearQuery.near(new Point(location[0],location[1])).maxDistance(new Distance(distance, Metrics.KILOMETERS).getNormalizedValue()).spherical(true), "location"),
                Aggregation.lookup("match", "_id", "saleId", "matches"),
                Aggregation.match(criteria.andOperator(Criteria.where("matches.userEmail").ne(this.userEmail)))
        );
    }
}
