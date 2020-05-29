package br.com.marco.cadeacerva.store.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class StoreDTO {
    private final String id;
    private final String name;

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
    }
}
