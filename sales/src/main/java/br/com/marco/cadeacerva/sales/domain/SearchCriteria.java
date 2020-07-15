package br.com.marco.cadeacerva.sales.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class SearchCriteria {

    private final double[] location;
    private final double distance;
    private final List<String> tags;
    private final Double pricePerLiter;

    public Criteria build() {
        Criteria criteria = Criteria.where("location")
                .nearSphere(new Point(location[0],location[1])).maxDistance(new Distance(distance, Metrics.KILOMETERS).getNormalizedValue());
        if(!CollectionUtils.isEmpty(tags)) {
            criteria.and("tags").in(tags);
        }
        if(Optional.ofNullable(pricePerLiter).isPresent()) {
            criteria.and("pricePerLiter").lte(pricePerLiter);
        }
        return criteria;
    }
}
