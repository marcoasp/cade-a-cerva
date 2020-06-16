package br.com.marco.cadeacerva.users.endpoints;

import br.com.marco.cadeacerva.users.utils.annotation.WithJwtUser;
import br.com.marco.cadeacerva.users.utils.JsonPayloadProvider;
import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.domain.UsersRepository;
import br.com.marco.cadeacerva.users.utils.annotation.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Test
    public void shouldCreateUnexistentUser() throws Exception {
        String email = "test@email.com";
        when(usersRepository.save(any())).thenReturn(new User(email));
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(
                put("/user/{email}", email)
                .with(csrf())
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
                        .with(csrf())
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
    public void shouldUpdateUserLocation() throws Exception {
        String email = "test@email.com";
        User currentUser = new User(email);
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(currentUser));
        when(usersRepository.save(currentUser)).then(i -> i.getArgument(0, User.class));
        mockMvc.perform(
                put("/user/me", email)
                        .with(csrf())
                        .content(JsonPayloadProvider.from(this.getClass(), "shouldUpdateUserLocation"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email", equalTo(email)))
        .andExpect(jsonPath("$.location", hasItems(10.0, 20.5)))
        ;

        verify(usersRepository).save(any());
    }

    @Test
    @WithJwtUser
    public void shouldReturn404ForNotExistingUser() throws Exception {
        String email = "test@email.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());
        mockMvc.perform(
                put("/user/me", email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(csrf())
        )
                .andExpect(status().isNotFound())
        ;

        verify(usersRepository, never()).save(any());
    }
}