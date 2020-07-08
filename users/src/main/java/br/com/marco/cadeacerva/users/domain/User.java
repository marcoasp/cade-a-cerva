package br.com.marco.cadeacerva.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class User {

    private String id;
    private final String email;
    private double[] location;
    private double area;
    private List<Interest> interests = new ArrayList<>();

    public User(final String id, final String email, final double[] location, final double area) {
        this.id = id;
        this.email = email;
        this.location = location;
        this.area = area;
    }

    public User update(final double[] location, final double area, List<Interest> interests) {
        this.location = location;
        this.area = area;
        this.interests.clear();
        this.interests.addAll(interests);
        return this;
    }
}
