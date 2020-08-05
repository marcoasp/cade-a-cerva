package br.com.marco.cadeacerva.matcher.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Document
public class User {

    @Id
    private String id;
    private final String email;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private double[] location;
    private double area;
    private List<Interest> interests = new ArrayList<>();

    public User(final String email, final double[] location, final double area) {
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
