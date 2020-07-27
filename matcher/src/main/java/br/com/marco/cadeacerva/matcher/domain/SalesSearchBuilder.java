package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.endpoints.dto.InterestDTO;
import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import org.springframework.util.CollectionUtils;

import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class SalesSearchBuilder {

    private final String unpagedString;

    public SalesSearchBuilder(final UserDTO user) {
        String commonString = String.format("location=%s,%s:distance=%s", user.getLocation()[0], user.getLocation()[1], user.getArea());
        this.unpagedString = user.getInterests().stream().map(i -> String.format("%s:%s", commonString, buildInterestString(i))).collect(joining("|"));
    }

    private String buildInterestString(InterestDTO interest) {
        StringBuilder builder = new StringBuilder();
        if(!CollectionUtils.isEmpty(interest.getTags())) {
            builder.append(String.format("tags=%s", interest.getTags().stream().collect(Collectors.joining(","))));
            Optional.ofNullable(interest.getPricePerLiter()).ifPresent(p -> builder.append(":"));
        }
        Optional.ofNullable(interest.getPricePerLiter()).ifPresent(p -> builder.append("pricePerLiter=" + p));

        return builder.toString();
    }

    public static SalesSearchBuilder from(final UserDTO user) {
        return new SalesSearchBuilder(user);
    }

    public String buildSearchString() {
        return this.unpagedString;
    }
}
