package br.com.marco.cadeacerva.matcher.domain.sale;

import br.com.marco.cadeacerva.matcher.infra.client.dto.SaleDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private final List<String> tags = new ArrayList<>();
    private final Double pricePerLiter;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private final double[] location;

    public Sale(final String address, final List<String> tags, final Double pricePerLiter, final double[] location) {
        this.address = address;
        this.tags.addAll(tags);
        this.pricePerLiter = pricePerLiter;
        this.location = location;
    }

    public static Sale of(final SaleDTO sale) {
        return new Sale(sale.getAddress(), sale.getTags(), sale.getPricePerLiter(), sale.getLocation());
    }
}
