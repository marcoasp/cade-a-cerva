package br.com.marco.cadeacerva.sales.endpoint;

import br.com.marco.cadeacerva.sales.domain.Sale;
import br.com.marco.cadeacerva.sales.domain.SaleRepository;
import br.com.marco.cadeacerva.sales.domain.SaleSearchCriteriaWrapper;
import br.com.marco.cadeacerva.testcommons.utils.JsonPayloadProvider;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import br.com.marco.cadeacerva.testcommons.utils.mockito.AnswerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@IntegrationTest
@WebMvcTest
@WithMockUser
public class SaleControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    SaleRepository saleRepository;

    @Test
    public void shouldCreateSale() throws Exception {
        when(saleRepository.save(any(Sale.class))).then(AnswerUtils.argWithId("random-string"));

        mvc.perform(
                post("/sale")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonPayloadProvider.from(SaleControllerTest.class, "shouldCreateSale"))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.address", equalTo("address")))
        .andExpect(jsonPath("$.tags",  hasItems("tag1", "tag2")))
        .andExpect(jsonPath("$.location", hasItems(10.0, 20.5)))
        .andExpect(jsonPath("$.pricePerLiter", equalTo(50.0)))
        ;
    }

    @Test
    public void shouldSearchForSale() throws Exception {
        when(saleRepository.findBy(any(SaleSearchCriteriaWrapper.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                    Arrays.asList(
                        new Sale("address", Arrays.asList("tag1", "tag2"), 10.0, new double[]{10.0, 20.0})
                    ), PageRequest.of(0, 1), 2));

        mvc.perform(
                get("/sale")
                .param("page", "0")
                .param("size", "1")
                .param("search",
            "location=10.01,10.02:distance=10.05:tags=abc,cde,dce:pricePerLiter=50.0|location=20.01,20.02:distance=20.05:tags=efg,hij:pricePerLiter=100.0"))
        .andDo(print())
        .andExpect(jsonPath("$.content[*]", hasSize(1)))
        .andExpect(jsonPath("$.content[0].address", equalTo("address")))
        .andExpect(jsonPath("$.content[0].tags", hasItems("tag1", "tag2")))
        .andExpect(jsonPath("$.content[0].pricePerLiter", equalTo(10.0)))
        .andExpect(jsonPath("$.content[0].location", hasItems(10.0, 20.0)))
        .andExpect(status().isOk());
    }
}