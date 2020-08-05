package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.domain.match.Match;
import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.matcher.infra.client.dto.SaleDTO;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class InterestMatcherTest {

    InterestMatcher matcher = new InterestMatcher();

    @Test
    public void shouldCreateMatches() {
//        UserDTO user = UserDTO.builder().email("test@email.com").build();
//        SaleDTO sale1 = SaleDTO.builder().address("address1").tags(Arrays.asList("tag1", "tag2")).pricePerLiter(10.0).location(new double[]{10.0, 20.0}).build();
//        SaleDTO sale2 = SaleDTO.builder().address("address2").tags(Arrays.asList("tag1", "tag2")).pricePerLiter(10.0).location(new double[]{10.0, 20.0}).build();
//        List<Match> matches = matcher.match(user, Arrays.asList(sale1, sale2));
//
//        assertThat(matches, hasSize(2));
//        assertThat(matches.get(0).getUserEmail(), equalTo("test@email.com"));
//        assertThat(matches.get(0).getSale().getAddress(), equalTo("address1"));
//        assertThat(matches.get(0).getSale().getTags(), hasItems("tag1", "tag2"));
//        assertThat(matches.get(0).getSale().getPricePerLiter(), equalTo(10.0));
//        assertThat(matches.get(0).getSale().getLocation()[0], equalTo(10.0));
//        assertThat(matches.get(0).getSale().getLocation()[1], equalTo(20.0));
//        assertThat(matches.get(0).getUserEmail(), equalTo("test@email.com"));
//        assertThat(matches.get(1).getSale().getAddress(), equalTo("address2"));
//        assertThat(matches.get(1).getSale().getTags(), hasItems("tag1", "tag2"));
//        assertThat(matches.get(1).getSale().getPricePerLiter(), equalTo(10.0));
//        assertThat(matches.get(1).getSale().getLocation()[0], equalTo(10.0));
//        assertThat(matches.get(1).getSale().getLocation()[1], equalTo(20.0));
    }
}