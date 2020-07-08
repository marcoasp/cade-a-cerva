package br.com.marco.cadeacerva.sales.domain;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaleSearchCriteriaWrapper {

    private final List<SearchCriteria> criterias = new ArrayList<>();

    public SaleSearchCriteriaWrapper(final SearchCriteria ... criterias) {
        this.criterias.addAll(Arrays.asList(criterias));
    }

    public Query createQuery() {
        List<Criteria> criterias = this.criterias.stream().map(SearchCriteria::build).collect(Collectors.toList());

        return new Query(new Criteria().orOperator(criterias.toArray(new Criteria[criterias.size()])));
    }
}
