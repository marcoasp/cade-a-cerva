package br.com.marco.cadeacerva.users.endpoints;

import br.com.marco.cadeacerva.users.domain.User;
import br.com.marco.cadeacerva.users.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.users.domain.UsersRepository;
import br.com.marco.cadeacerva.users.endpoints.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

    @PutMapping("/me")
    public UserDTO update(@AuthenticationPrincipal Jwt auth, @RequestBody UserDTO userDTO) {
        User user = usersRepository.findByEmail(auth.getClaimAsString("email"))
                .map((foundUser) -> usersRepository.save(foundUser.update(userDTO.getLocation(), userDTO.getArea())))
                .orElseThrow(NotFoundException::new);
        return new UserDTO(user);
    }
}
