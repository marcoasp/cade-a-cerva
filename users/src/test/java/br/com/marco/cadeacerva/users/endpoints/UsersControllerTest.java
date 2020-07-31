package br.com.marco.cadeacerva.users.endpoints;

import br.com.marco.cadeacerva.testcommons.utils.JsonPayloadProvider;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import br.com.marco.cadeacerva.testcommons.utils.annotation.WithJwtUser;
import br.com.marco.cadeacerva.users.application.UserApplicationService;
import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.domain.UserProducer;
import br.com.marco.cadeacerva.users.domain.UsersRepository;
import br.com.marco.cadeacerva.users.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.users.endpoints.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@IntegrationTest
@WebMvcTest
@WithMockUser
public class UsersControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UsersRepository usersRepository;

    @MockBean
    UserProducer userProducer;

    @MockBean
    UserApplicationService userApplicationService;

    @Test
    public void shouldCreateUnexistentUser() throws Exception {
        String email = "test@email.com";
        when(usersRepository.save(any())).thenReturn(new User(email));
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(
                put("/user/{email}", email)
                .content(JsonPayloadProvider.from(this.getClass(), "shouldCreateUnexistentUser"))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email", equalTo(email)))
        ;

        verify(usersRepository).save(any());
    }

    @Test
    public void shouldReturnExistingUserOnPut() throws Exception {
        String email = "test@email.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(new User(email)));
        mockMvc.perform(
                put("/user/{email}", email)
                        .content(JsonPayloadProvider.from(this.getClass(), "shouldCreateUnexistentUser"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email", equalTo(email)))
        ;

        verify(usersRepository, never()).save(any());
    }

    @Test
    @WithJwtUser
    public void shouldUpdateUser() throws Exception {
        when(userApplicationService.updateUser(anyString(), any())).then(i -> i.getArgument(1, UserDTO.class));
        mockMvc.perform(
                put("/user/me")
                        .content(JsonPayloadProvider.from(this.getClass(), "shouldUpdateUser"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email", equalTo("email@email.com")))
        .andExpect(jsonPath("$.location", hasItems(10.0, 20.5)))
        .andExpect(jsonPath("$.area", equalTo(3.5)))
        .andExpect(jsonPath("$.interests.[0].tags", hasItems("beer1", "beer2")))
        .andExpect(jsonPath("$.interests.[0].pricePerLiter", equalTo(5.5)))
        ;

        verify(userApplicationService).updateUser(anyString(), any());
    }

    @Test
    @WithJwtUser
    public void shouldReturn404ForNotExistingUser() throws Exception {
        String email = "test@email.com";
        when(userApplicationService.updateUser(anyString(), any())).thenThrow(new NotFoundException());
        mockMvc.perform(
                put("/user/me", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        )
                .andExpect(status().isNotFound())
        ;
    }
}