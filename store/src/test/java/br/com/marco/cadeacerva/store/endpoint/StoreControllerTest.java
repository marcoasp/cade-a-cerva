package br.com.marco.cadeacerva.store.endpoint;

import br.com.marco.cadeacerva.store.domain.Store;
import br.com.marco.cadeacerva.store.domain.StoreRepository;
import br.com.marco.cadeacerva.testcommons.utils.JsonPayloadProvider;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static br.com.marco.cadeacerva.testcommons.utils.mockito.AnswerUtils.argWithId;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@IntegrationTest
@WebMvcTest
@WithMockUser
class StoreControllerTest {

    @MockBean
    StoreRepository repository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldListPagedStores() throws Exception {
        Store mockedStore = mock(Store.class);
        when(mockedStore.getId()).thenReturn("random-string");
        when(mockedStore.getName()).thenReturn("store");

        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(mockedStore, mockedStore)));

        mockMvc.perform(get("/store"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[*]", hasSize(2)))
            .andExpect(jsonPath("$.content[*].id", notNullValue()))
            .andExpect(jsonPath("$.content[*].name", notNullValue()));
    }

    @Test
    void shouldSaveAStore() throws Exception {
        when(repository.save(any(Store.class)))
                .then(argWithId("random-string"));

        mockMvc.perform(post("/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonPayloadProvider.from(this.getClass(), "shouldSaveAStore"))

        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", equalTo("store")));
    }

    @Test
    void shouldFindAStore() throws Exception {
        Store mockedStore = mock(Store.class);
        when(mockedStore.getId()).thenReturn("random-string");
        when(mockedStore.getName()).thenReturn("store");

        when(repository.findById(anyString())).thenReturn(Optional.of(mockedStore));

        mockMvc.perform(get("/store/random-string"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", equalTo("random-string")))
        .andExpect(jsonPath("$.name", equalTo("store")))
        ;
    }

    @Test
    void shouldReturn404WhenStoreDoesNotExists() throws Exception {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(get("/store/random-string"))
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    void shouldDeleteStore() throws Exception {
        when(repository.findById("random-string")).thenReturn(Optional.of(new Store("store")));
        mockMvc.perform(delete("/store/random-string"))
                .andExpect(status().isNoContent());
        verify(repository).delete(any(Store.class));
    }

    @Test
    void shouldReturn404WhenDeleteNotExistentStore() throws Exception {
        mockMvc.perform(delete("/store/random-string"))
                .andExpect(status().isNotFound());
        verify(repository, never()).delete(any(Store.class));
    }

    @Test
    void shouldUpdateStoreName() throws Exception {
        when(repository.findById("random-string"))
                .thenReturn(Optional.of(new Store("store")));
        when(repository.save(any(Store.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(put("/store/random-string")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonPayloadProvider.from(this.getClass(), "shouldUpdateStoreName"))

        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("store-modified")));

        verify(repository).save(any(Store.class));
    }

    @Test
    void shouldReturn404WhenUpdatingNotExistentStore() throws Exception {
        when(repository.findById("random-string"))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/store/random-string")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")

        )
        .andExpect(status().isNotFound());

        verify(repository, never()).save(any(Store.class));
    }
}