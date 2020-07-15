package br.com.marco.cadeacerva.sales.infra.converter;

import br.com.marco.cadeacerva.sales.domain.SaleSearchCriteriaWrapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SaleSearchCriteriaWrapperConverterTest {

    @Test
    public void shouldConvertSingleFilter() {
        String source = "location=10.01,10.02:distance=10.05:tags=abc,cde,dce:pricePerLiter=50.0";
        SaleSearchCriteriaWrapperConverter converter = new SaleSearchCriteriaWrapperConverter();
        SaleSearchCriteriaWrapper filter = converter.convert(source);

        assertThat(filter.getCriterias().get(0).getLocation()[0], equalTo(10.01));
        assertThat(filter.getCriterias().get(0).getLocation()[1], equalTo(10.02));
        assertThat(filter.getCriterias().get(0).getDistance(), equalTo(10.05));
        assertThat(filter.getCriterias().get(0).getTags(), hasItems("abc", "dce"));
        assertThat(filter.getCriterias().get(0).getPricePerLiter(), equalTo(50.0));
    }

    @Test
    public void shouldConvertIgnoreOptionalFilters() {
        String source = "location=10.01,10.02:distance=10.05";
        SaleSearchCriteriaWrapperConverter converter = new SaleSearchCriteriaWrapperConverter();
        SaleSearchCriteriaWrapper filter = converter.convert(source);

        assertThat(filter.getCriterias().get(0).getLocation()[0], equalTo(10.01));
        assertThat(filter.getCriterias().get(0).getLocation()[1], equalTo(10.02));
        assertThat(filter.getCriterias().get(0).getDistance(), equalTo(10.05));
        assertThat(filter.getCriterias().get(0).getTags(), nullValue());
        assertThat(filter.getCriterias().get(0).getPricePerLiter(), nullValue());
    }

    @Test
    public void shouldConvertMultiple() {
        String source = "location=10.01,10.02:distance=10.05:tags=abc,cde,dce:pricePerLiter=50.0|location=20.01,20.02:distance=20.05:tags=efg,hij:pricePerLiter=100.0";
        SaleSearchCriteriaWrapperConverter converter = new SaleSearchCriteriaWrapperConverter();
        SaleSearchCriteriaWrapper filter = converter.convert(source);

        assertThat(filter.getCriterias().get(0).getLocation()[0], equalTo(10.01));
        assertThat(filter.getCriterias().get(0).getLocation()[1], equalTo(10.02));
        assertThat(filter.getCriterias().get(0).getDistance(), equalTo(10.05));
        assertThat(filter.getCriterias().get(0).getTags(), hasItems("abc", "dce"));
        assertThat(filter.getCriterias().get(0).getPricePerLiter(), equalTo(50.0));

        assertThat(filter.getCriterias().get(1).getLocation()[0], equalTo(20.01));
        assertThat(filter.getCriterias().get(1).getLocation()[1], equalTo(20.02));
        assertThat(filter.getCriterias().get(1).getDistance(), equalTo(20.05));
        assertThat(filter.getCriterias().get(1).getTags(), hasItems("efg", "hij"));
        assertThat(filter.getCriterias().get(1).getPricePerLiter(), equalTo(100.0));
    }
}