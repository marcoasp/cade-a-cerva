package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.domain.sale.SaleRepository;
import br.com.marco.cadeacerva.matcher.domain.sale.SaleSearchCriteriaWrapper;
import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInterestsAggregator {

    private final SaleRepository saleRepository;

    public void aggregate(final UserDTO user) {
        saleRepository.findBy(SaleSearchCriteriaWrapper.from(user));
    }
}
