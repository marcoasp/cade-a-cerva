package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.domain.match.Match;
import br.com.marco.cadeacerva.matcher.domain.match.MatchRepository;
import br.com.marco.cadeacerva.matcher.domain.sale.Sale;
import br.com.marco.cadeacerva.matcher.domain.sale.SaleRepository;
import br.com.marco.cadeacerva.matcher.domain.sale.SaleSearchCriteriaWrapper;
import br.com.marco.cadeacerva.matcher.domain.sale.SearchCriteria;
import br.com.marco.cadeacerva.matcher.domain.user.User;
import br.com.marco.cadeacerva.matcher.domain.user.UserRepository;
import br.com.marco.cadeacerva.matcher.infra.listener.MongoIndexCreationListener;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@IntegrationTest
@Import(MongoIndexCreationListener.class)
@DataMongoTest
public class SaleRepositoryTest {

    @Autowired
    SaleRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MatchRepository matchRepository;

    @Before
    public void setUp() {
        List<User> users = Arrays.asList(
                new User("user1@email.com"),
                new User("user2@email.com")
        ).stream().peek(userRepository::save).collect(toList());

        List<Sale> sales = Arrays.asList(
                new Sale("address1", Arrays.asList("tag1"), 5.0, new double[]{-23.2192762, -45.8543312}),
                new Sale("address2", Collections.emptyList(), 10.0, new double[]{-23.2192762, -45.8543312}),
                new Sale("address3", Collections.emptyList(), 20.0, new double[]{-23.2219481, -45.8526897}),
                new Sale("address4", Collections.emptyList(), 20.0, new double[]{-23.2219481, -45.8526897}),
                new Sale("address5", Collections.emptyList(), 20.0, new double[]{-90.0, -90.0})
        ).stream().peek(repository::save).collect(toList());

        Arrays.asList(new Match(users.get(0).getEmail(), sales.get(0).getId(), users.get(0).getId())).forEach(matchRepository::save);
    }

    @After
    public void delete() {
        repository.deleteAll();
        userRepository.deleteAll();
        matchRepository.deleteAll();
    }

    @Test
    public void shouldNotConsideredAlreadyExistingMatches() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper("user1@email.com",
                new double[]{-23.2023046,-45.8639857},
                10, Collections.emptyList());
        List<Sale> page = repository.findBy(criteria);

        assertThat(page.size(), equalTo(3));
        assertThat(page.stream().map(Sale::getAddress).collect(toList()), hasItems("address2", "address3", "address4"));
    }

    @Test
    public void shouldReturnNearResults() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper("user2@email.com",
                new double[]{-23.2023046,-45.8639857},
                10, Collections.emptyList());
        List<Sale> page = repository.findBy(criteria);

        assertThat(page.size(), equalTo(4));
        assertThat(page.stream().map(Sale::getAddress).collect(toList()), hasItems("address1", "address2", "address3", "address4"));
    }

    @Test
    public void shouldSearchByTagOnly() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper("user2@email.com",
                new double[]{-23.2023046,-45.8639857},
                10,
                Arrays.asList(SearchCriteria.builder().tags(Arrays.asList("tag1")).build()));
        List<Sale> sales = repository.findBy(criteria);

        assertThat(sales.size(), equalTo(1));
        assertThat(sales.get(0).getTags(), hasItems("tag1"));
    }

    @Test
    public void shouldSearchByPriceOnly() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper("user2@email.com",
                new double[]{-23.2023046,-45.8639857},
                10,
                Arrays.asList(SearchCriteria.builder().pricePerLiter(10.0).build()));
        List<Sale> sales = repository.findBy(criteria);

        assertThat(sales.size(), equalTo(2));
        assertThat(sales.get(0).getPricePerLiter(), equalTo(5.0));
        assertThat(sales.get(1).getPricePerLiter(), equalTo(10.0));
    }

    @Test
    public void shouldSearchByTagAndPrice() {
        SaleSearchCriteriaWrapper criteria = new SaleSearchCriteriaWrapper(
                "user2@email.com",
                new double[]{-23.2023046,-45.8639857},
                10,
                Arrays.asList(SearchCriteria.builder().tags(Arrays.asList("tag1")).pricePerLiter(10.0).build()));
        List<Sale> sales = repository.findBy(criteria);

        assertThat(sales.size(), equalTo(1));
        assertThat(sales.get(0).getPricePerLiter(), equalTo(5.0));
    }
}