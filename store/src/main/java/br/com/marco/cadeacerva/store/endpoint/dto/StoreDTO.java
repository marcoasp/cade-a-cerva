package br.com.marco.cadeacerva.store.endpoint.dto;

import br.com.marco.cadeacerva.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class StoreDTO {
    private final String id;
    private final String name;

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
    }
}
