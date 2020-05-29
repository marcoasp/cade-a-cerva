package br.com.marco.cadeacerva.sales.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Sale {

    private String id;
    private final String address;
    private final List<String> tags;
    private final double[] location;

    public Sale(final String address, final List<String> tags, final double[] location) {
        this.address = address;
        this.tags = new ArrayList<>(tags);
        this.location = location;
    }
}
