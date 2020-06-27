package br.com.marco.cadeacerva.gateway.infra.client;

import br.com.marco.cadeacerva.gateway.infra.client.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("users")
public interface UsersClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{email}")
    UserDTO saveUser(@RequestHeader("Authorization") String token, @PathVariable("email") String email, UserDTO user);
}
