package br.com.marco.cadeacerva.store.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author marprado - Marco Prado
 * @version 1.0 29/05/2020
 */
@RequiredArgsConstructor
@Getter
@Accessors(chain = true)
public class Store {

    private String id;
    @Setter
    private String name;

    public Store(final String name) {
        this.name = name;
    }
}
