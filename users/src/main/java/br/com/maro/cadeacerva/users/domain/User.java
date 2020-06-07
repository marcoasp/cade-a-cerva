package br.com.maro.cadeacerva.users.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class User {

    private String id;
    private final String email;
}
