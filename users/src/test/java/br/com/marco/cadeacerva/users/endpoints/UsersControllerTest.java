package br.com.marco.cadeacerva.users.endpoints;

import br.com.marco.cadeacerva.users.utils.JsonPayloadProvider;
import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.domain.UsersRepository;
import br.com.marco.cadeacerva.users.infra.annotations.IntegrationTest;
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
        String email = "email@email.com";
        when(usersRepository.save(any())).thenReturn(new User(email));
        when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        mockMvc.perform(
                put("/user/{email}", email)
                .with(csrf())
                .content(JsonPayloadProvider.from(this.getClass(), "shouldCreateUnexistentUser"))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email", equalTo(email)))
        ;

        verify(usersRepository).save(any());
    }

    @Test
    public void shouldReturnExistingUserOnPut() throws Exception {
        String email = "email@email.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(new User(email)));
        mockMvc.perform(
                put("/user/{email}", email)
                        .with(csrf())
                        .content(JsonPayloadProvider.from(this.getClass(), "shouldCreateUnexistentUser"))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", equalTo(email)))
        ;

        verify(usersRepository, never()).save(any());
    }
}