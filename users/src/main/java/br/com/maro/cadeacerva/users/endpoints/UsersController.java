package br.com.maro.cadeacerva.users.endpoints;

import br.com.maro.cadeacerva.users.domain.User;
import br.com.maro.cadeacerva.users.endpoints.dto.UserDTO;
import br.com.maro.cadeacerva.users.domain.UsersRepository;
import br.com.maro.cadeacerva.users.endpoints.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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

    @GetMapping("/oidc-principal")
    public JwtAuthenticationToken getOidcUserPrincipal(@AuthenticationPrincipal JwtAuthenticationToken principal) {
        return principal;
    }

    @PutMapping("/id")
    public UserDTO update(@PathVariable("id") String id, @RequestBody UserDTO userDTO) {
        User user = usersRepository.findById(id)
        .map((u) -> u.update(userDTO.getLocation()))
        .orElseThrow(NotFoundException::new);
        return new UserDTO(user);
    }
}
