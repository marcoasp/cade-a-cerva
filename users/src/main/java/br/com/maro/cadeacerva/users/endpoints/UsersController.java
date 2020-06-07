package br.com.maro.cadeacerva.users.endpoints;

import br.com.maro.cadeacerva.users.domain.User;
import br.com.maro.cadeacerva.users.endpoints.dto.UserDTO;
import br.com.maro.cadeacerva.users.domain.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsersController {

    private final UsersRepository usersRepository;

    @PutMapping("/{email}")
    public UserDTO save(@PathVariable("email") String email, @RequestBody UserDTO userDto) {
        User user = usersRepository.findByEmail(email).orElseGet(() ->
                usersRepository.save(new User(userDto.getEmail()))
        );
        return new UserDTO(user);
    }
}
