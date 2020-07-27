package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.endpoints.dto.UserDTO;
import br.com.marco.cadeacerva.matcher.infra.client.SalesClient;
import br.com.marco.cadeacerva.matcher.infra.client.dto.SaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInterestsAggregator {

    private final SalesClient salesClient;
    private final MatchRepository matchRepository;
    private final InterestMatcher interestMatcher;

    public void aggregate(final UserDTO user) {
        PageRequest firstPage = PageRequest.of(0, 10);
        String searchString = SalesSearchBuilder.from(user).buildSearchString();

        Page<SaleDTO> result = salesClient.searchSales(searchString, firstPage);
        while(!result.isEmpty()) {
            List<Match> matches = interestMatcher.match(user, result.getContent());
            matchRepository.saveAll(matches);
            result = salesClient.searchSales(searchString, result.nextPageable());
        }
    }
}
