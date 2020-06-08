package br.com.maro.cadeacerva.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
