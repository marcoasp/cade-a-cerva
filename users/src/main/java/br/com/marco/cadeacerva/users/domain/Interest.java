package br.com.marco.cadeacerva.users.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Interest {
    private List<String> tags = new ArrayList<>();
    private Double pricePerLiter;

    public Interest(final List<String> tags, final Double pricePerLiter) {
        this.tags.addAll(tags);
        this.pricePerLiter = pricePerLiter;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }
}
