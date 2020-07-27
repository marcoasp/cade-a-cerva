package br.com.marco.cadeacerva.matcher.infra.client.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SaleDTO {
    private final String id;
    private final String address;
    private final List<String> tags;
    private final Double pricePerLiter;
    private final double[] location;
}
