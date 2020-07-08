package br.com.marco.cadeacerva.sales.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document
public class Sale {

    @Id
    private String id;
    private final String address;
    private final List<String> tags;
    private final Double pricePerLiter;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private final double[] location;

    public Sale(final String address, final List<String> tags, final Double pricePerLiter, final double[] location) {
        this.address = address;
        this.tags = new ArrayList<>(tags);
        this.pricePerLiter = pricePerLiter;
        this.location = location;
    }
}
