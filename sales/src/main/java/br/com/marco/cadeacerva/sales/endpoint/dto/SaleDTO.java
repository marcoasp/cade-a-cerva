package br.com.marco.cadeacerva.sales.endpoint.dto;

import br.com.marco.cadeacerva.sales.domain.Sale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class SaleDTO {
    private final String id;
    private final String address;
    private final List<String> tags;
    private final Double pricePerLiter;
    private final double[] location;

    public SaleDTO(final Sale sale) {
        this(sale.getId(), sale.getAddress(), new ArrayList<>(sale.getTags()), sale.getPricePerLiter(), sale.getLocation());
    }

    public static SaleDTO from(final Sale sale) {
        return new SaleDTO(sale);
    }
}
