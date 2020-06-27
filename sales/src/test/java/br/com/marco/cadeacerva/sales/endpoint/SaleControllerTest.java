package br.com.marco.cadeacerva.sales.endpoint;

import br.com.marco.cadeacerva.sales.domain.Sale;
import br.com.marco.cadeacerva.sales.domain.SaleRepository;
import br.com.marco.cadeacerva.testcommons.utils.JsonPayloadProvider;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import br.com.marco.cadeacerva.testcommons.utils.mockito.AnswerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        .andExpect(jsonPath("$.location", hasItems(10.0, 20.5)));
    }
}