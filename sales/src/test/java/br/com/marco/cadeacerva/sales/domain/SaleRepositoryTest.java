package br.com.marco.cadeacerva.sales.domain;

import br.com.marco.cadeacerva.sales.infra.listener.MongoIndexCreationListener;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
            new Sale("address1", Collections.emptyList(), new double[]{-23.2192762,-45.8543312}),
            new Sale("address2", Collections.emptyList(), new double[]{-23.2192762,-45.8543312}),
            new Sale("address3", Collections.emptyList(), new double[]{-23.2219481,-45.8526897}),
            new Sale("address4", Collections.emptyList(), new double[]{-23.2219481,-45.8526897}),
            new Sale("address5", Collections.emptyList(), new double[]{-90.0, -90.0})
        ).stream().forEach(repository::save);

    }

    @Test
    public void shouldReturnPagedNearResults() {
        Page<Sale> page = repository.findByLocationNear(new Point(-23.2023046,-45.8639857), new Distance(10, Metrics.KILOMETERS), PageRequest.of(0, 2));

        assertThat(page.getSize(), equalTo(2));
        assertThat(page.getTotalElements(), equalTo(4L));
        assertThat(page.map(Sale::getAddress).getContent(), hasItems("address1", "address2"));
    }
}