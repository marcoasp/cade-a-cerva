package br.com.marco.cadeacerva.matcher.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Match {
    private final String id;
    private final String userEmail;
    private final Sale sale;
}
