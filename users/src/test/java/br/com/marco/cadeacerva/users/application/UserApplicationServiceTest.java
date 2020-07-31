package br.com.marco.cadeacerva.users.application;

import br.com.marco.cadeacerva.users.domain.Interest;
import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.domain.UserProducer;
import br.com.marco.cadeacerva.users.domain.UsersRepository;
import br.com.marco.cadeacerva.users.endpoints.dto.InterestDTO;
import br.com.marco.cadeacerva.users.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.users.endpoints.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserApplicationServiceTest {

    @Mock
    UserProducer userProducer;

    @Mock
    UsersRepository userRepository;

    @InjectMocks
    UserApplicationService service;

    @Test
    public void shouldUpdateUserAndEmitEvent() {
        String email = "test@email.com";
        User currentUser = new User(email);

        UserDTO updateDto = mock(UserDTO.class);
        when(updateDto.getArea()).thenReturn(10.0);
        when(updateDto.getLocation()).thenReturn(new double[]{10.0, 20.0});
        when(updateDto.getInterests()).thenReturn(Arrays.asList(InterestDTO.from(Interest.of(20.0, "tag1", "tag2"))));

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(currentUser));
        when(userRepository.save(any(User.class))).then(i -> i.getArgument(0, User.class));

        UserDTO result = service.updateUser(email, updateDto);

        assertThat(result.getArea(), equalTo(10.0));
        assertThat(result.getLocation()[0], equalTo(10.0));
        assertThat(result.getLocation()[1], equalTo(20.0));
        assertThat(result.getInterests().get(0).getPricePerLiter(), equalTo(20.0));
        assertThat(result.getInterests().get(0).getTags(), hasItems("tag1", "tag2"));

        verify(userRepository).save(any());
        verify(userProducer).produceUserMessage(any(User.class));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowNotFoundOnUpdatingUnexistentUser() {
        UserDTO updateDto = mock(UserDTO.class);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        service.updateUser("email", updateDto);

        verify(userRepository).save(any());
        verify(userProducer).produceUserMessage(any(User.class));
    }
}