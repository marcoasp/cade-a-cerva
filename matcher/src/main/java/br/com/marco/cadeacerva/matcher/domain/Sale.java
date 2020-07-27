package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.infra.client.dto.SaleDTO;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Sale {
    private final String address;
    private final List<String> tags = new ArrayList<>();
    private final Double pricePerLiter;
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
