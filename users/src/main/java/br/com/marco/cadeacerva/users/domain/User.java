package br.com.marco.cadeacerva.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User {

    private String id;
    private final String email;
    private double[] location;

    public User update(final double[] location) {
        this.location = location;
        return this;
    }
}
