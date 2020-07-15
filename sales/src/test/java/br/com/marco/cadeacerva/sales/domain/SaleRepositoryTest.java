package br.com.marco.cadeacerva.sales.domain;

import br.com.marco.cadeacerva.sales.infra.listener.MongoIndexCreationListener;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@IntegrationTest
@Import(MongoIndexCreationListener.class)
@DataMongoTest
public class SaleRepositoryTest {

    @Autowired
    SaleRepository repository;

    @Before
    public void setUp() {
        Arrays.asList(
            new Sale("address1", Arrays.asList("tag1"), 5.0, new double[]{-23.2192762,-45.8543312}),
            new Sale("address2", Collections.emptyList(), 10.0, new double[]{-23.2192762,-45.8543312}),
            new Sale("address3", Collections.emptyList(), 20.0, new double[]{-23.2219481,-45.8526897}),
            new Sale("address4", Collections.emptyList(), 20.0, new double[]{-23.2219481,-45.8526897}),
            new Sale("address5", Collections.emptyList(), 20.0, new double[]{-90.0, -90.0})
        ).stream().forEach(repository::save);
    }

    @After
    public void delete() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnPagedNearResults() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper(Arrays.asList(SearchCriteria.builder().location(new double[]{-23.2023046,-45.8639857}).distance(10).build()));
        Page<Sale> page = repository.findBy(criteria, PageRequest.of(0, 2));

        assertThat(page.getSize(), equalTo(2));
        assertThat(page.getTotalElements(), equalTo(4L));
        assertThat(page.map(Sale::getAddress).getContent(), hasItems("address1", "address2"));
    }

    @Test
    public void shouldSearchByTagOnly() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper(Arrays.asList(SearchCriteria.builder().location(new double[]{-23.2023046,-45.8639857}).distance(10).tags(Arrays.asList("tag1")).build()));
        Page<Sale> sales = repository.findBy(criteria, PageRequest.of(0, 2));

        assertThat(sales.getTotalElements(), equalTo(1L));
        assertThat(sales.getContent().get(0).getTags(), hasItems("tag1"));
    }

    @Test
    public void shouldSearchByPriceOnly() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper(Arrays.asList(SearchCriteria.builder().location(new double[]{-23.2023046,-45.8639857}).distance(10).pricePerLiter(10.0).build()));
        Page<Sale> sales = repository.findBy(criteria, PageRequest.of(0, 2));

        assertThat(sales.getTotalElements(), equalTo(2L));
        assertThat(sales.getContent().get(0).getPricePerLiter(), equalTo(5.0));
        assertThat(sales.getContent().get(1).getPricePerLiter(), equalTo(10.0));
    }

    @Test
    public void shouldSearchByTagAndPrice() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper(
                Arrays.asList(SearchCriteria.builder().tags(Arrays.asList("tag1")).location(new double[]{-23.2023046,-45.8639857}).distance(10).pricePerLiter(10.0).build()));
        Page<Sale> sales = repository.findBy(criteria, PageRequest.of(0, 2));

        assertThat(sales.getTotalElements(), equalTo(1L));
        assertThat(sales.getContent().get(0).getPricePerLiter(), equalTo(5.0));
    }
}