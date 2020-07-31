package br.com.marco.cadeacerva.users.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public class Interest {
    private final List<String> tags = new ArrayList<>();
    private final Double pricePerLiter;

    public Interest(final List<String> tags, final Double pricePerLiter) {
        this.tags.addAll(tags);
        this.pricePerLiter = pricePerLiter;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public static Interest of(Double pricePerLiter, String ... tags) {
        return new Interest(Arrays.asList(tags), pricePerLiter);
    }
}
