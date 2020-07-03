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

    public User(final String id, final String email, final double[] location, final double area) {
        this.id = id;
        this.email = email;
        this.location = location;
        this.area = area;
    }

    public User update(final double[] location, final double area) {
        this.location = location;
        this.area = area;
        return this;
    }
}
