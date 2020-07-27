package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.endpoints.dto.InterestDTO;
import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SalesSearchBuilderTest {

    @Test
    public void shouldCreateSearchStringFromUser() {
        InterestDTO interest1 = mock(InterestDTO.class);
        when(interest1.getPricePerLiter()).thenReturn(10.0);
        when(interest1.getTags()).thenReturn(Arrays.asList("beer1", "beer2"));

        InterestDTO interest2 = mock(InterestDTO.class);
        when(interest2.getPricePerLiter()).thenReturn(10.0);

        UserDTO user = mock(UserDTO.class);
        when(user.getLocation()).thenReturn(new double[]{10.0, 20.0});
        when(user.getArea()).thenReturn(50.0);
        when(user.getInterests()).thenReturn(Arrays.asList(interest1, interest2));

        String searchString = SalesSearchBuilder.from(user).buildSearchString();

        assertThat(searchString, equalTo("location=10.0,20.0:distance=50.0:tags=beer1,beer2:pricePerLiter=10.0|location=10.0,20.0:distance=50.0:pricePerLiter=10.0"));
    }
}