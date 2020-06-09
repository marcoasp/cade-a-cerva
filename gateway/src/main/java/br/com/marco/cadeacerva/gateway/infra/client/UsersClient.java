package br.com.marco.cadeacerva.gateway.infra.client;

import br.com.marco.cadeacerva.gateway.infra.client.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("users")
public interface UsersClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/api/user/{email}")
    UserDTO saveUser(@PathVariable("email") String email, UserDTO user);
}
