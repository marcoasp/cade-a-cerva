package br.com.marco.cadeacerva.users.application;

import br.com.marco.cadeacerva.users.domain.Interest;
import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.domain.UserProducer;
import br.com.marco.cadeacerva.users.domain.UsersRepository;
import br.com.marco.cadeacerva.users.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.users.endpoints.exception.NotFoundException;
import br.com.marco.cadeacerva.users.infra.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import static java.util.stream.Collectors.toList;

@ApplicationService
@RequiredArgsConstructor
public class UserApplicationService {

    private final UserProducer userProducer;
    private final UsersRepository usersRepository;

    public  UserDTO updateUser(@AuthenticationPrincipal final String email, final UserDTO userDTO) {
        User user = usersRepository.findByEmail(email)
                .map((foundUser) -> usersRepository.save(foundUser.update(
                        userDTO.getLocation(),
                        userDTO.getArea(),
                        userDTO.getInterests().stream().map(ui -> new Interest(ui.getTags(), ui.getPricePerLiter())).collect(toList()))
                ))
                .orElseThrow(NotFoundException::new);
        userProducer.produceUserMessage(user);
        return new UserDTO(user);
    }

}
