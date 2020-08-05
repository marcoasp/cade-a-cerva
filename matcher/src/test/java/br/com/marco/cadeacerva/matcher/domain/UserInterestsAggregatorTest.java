package br.com.marco.cadeacerva.matcher.domain;

import br.com.marco.cadeacerva.matcher.domain.match.MatchRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserInterestsAggregatorTest {

    @Mock
    MatchRepository matchRepository;

    @Mock
    InterestMatcher interestMatcher;

    @InjectMocks
    UserInterestsAggregator aggregator;

//    @Test
    public void shouldAggregateNewUserInterests() {
//        SaleDTO saleMock = mock(SaleDTO.class);
//        Match match = mock(Match.class);
//        UserDTO user = UserDTO.builder().location(new double[]{10.0, 20.0}).area(10.0).build();
//
//        when(salesClient.searchSales(anyString(), any(Pageable.class)))
//                .thenReturn(new PageImpl<>(Arrays.asList(saleMock)))
//                .thenReturn(new PageImpl<>(Collections.emptyList()));
//        when(interestMatcher.match(any(UserDTO.class), anyList())).thenReturn(Arrays.asList(match));
//
//        aggregator.aggregate(user);
//
//        verify(salesClient, times(2)).searchSales(anyString(), any());
//        verify(interestMatcher).match(any(), anyList());
//        verify(matchRepository).saveAll(anyList());
    }
}