package br.com.marco.cadeacerva.matcher.domain;

import java.util.ArrayList;
import java.util.List;

public class Interest {
    private final List<String> tags = new ArrayList<>();
    private final Double pricePerLiter;

    public Interest(final List<String> tags, final Double pricePerLiter) {
        this.tags.addAll(tags);
        this.pricePerLiter = pricePerLiter;
    }
}
