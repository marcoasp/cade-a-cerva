package br.com.marco.cadeacerva.matcher.infra.client;

import br.com.marco.cadeacerva.matcher.infra.client.dto.SaleDTO;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;

@RunWith(SpringRunner.class)
@IntegrationTest
@SpringBootTest
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.LOCAL,
        ids = "br.com.marco.cadeacerva:sales:+:stubs:8090")
public class SalesClientTest {

    @Autowired
    SalesClient client;

    @Test
    public void shouldSearchForSales() {
        Page<SaleDTO> sales = client.searchSales("location=10.01,10.02:distance=10.05:tags=abc,cde,dce:pricePerLiter=50.0", PageRequest.of(0, 1));
        SaleDTO sale = sales.getContent().get(0);
        assertThat(sales.getSize(), equalTo(1));
        assertThat(sale.getAddress(), notNullValue());
        assertThat(sale.getTags(), hasSize(greaterThan(0)));
        assertThat(sale.getPricePerLiter(), notNullValue());
        assertThat(sale.getLocation(), notNullValue());
    }
}