package br.com.marco.cadeacerva.sales.infra.converter;

import br.com.marco.cadeacerva.sales.domain.SaleSearchCriteriaWrapper;
import br.com.marco.cadeacerva.sales.domain.SearchCriteria;
import io.micrometer.core.instrument.search.Search;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Component
public class SaleSearchCriteriaWrapperConverter implements Converter<String, SaleSearchCriteriaWrapper> {

    @Override
    public SaleSearchCriteriaWrapper convert(final String source) {
        //location=10.01,10.02:distance=10.05:tags=abc,cde,dce:pricePerLiter=50.0|location=10.01,10.02:distance=10.05:tags=abc,cde,dce:pricePerLiter=50.0
        List<SearchCriteria> search = Arrays.asList(source.split("\\|")).stream().map(f -> parseFilter(f)).collect(toList());
        return new SaleSearchCriteriaWrapper(search);
    }

    private SearchCriteria parseFilter(String filter) {
        Map<String, String> filters = Arrays.asList(filter.split(":")).stream().collect(toMap(f -> f.split("=")[0], f -> f.split("=")[1]));
        double[] location = Arrays.asList(filters.get("location").split(",")).stream().mapToDouble(l -> Double.parseDouble(l)).toArray();
        double distance = Double.parseDouble(filters.get("distance"));
        SearchCriteria.SearchCriteriaBuilder builder = SearchCriteria.builder()
                .location(location)
                .distance(distance);
        Optional.ofNullable(filters.get("tags")).ifPresent(v -> builder.tags(Arrays.asList(v.split(","))));
        Optional.ofNullable(filters.get("pricePerLiter")).ifPresent(v -> builder.pricePerLiter(Double.parseDouble(v)));
        return builder.build();
    }
}
