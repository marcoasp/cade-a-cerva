package br.com.marco.cadeacerva.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User {

    private String id;
    private final String email;
    private double[] location;
    private double area;

    public User update(final double[] location, final double area) {
        this.location = location;
        this.area = area;
        return this;
    }
}
