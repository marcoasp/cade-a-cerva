package br.com.marco.cadeacerva.gateway.infra.client.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class UserDTO {

    public String id;
    public final String email;
}
