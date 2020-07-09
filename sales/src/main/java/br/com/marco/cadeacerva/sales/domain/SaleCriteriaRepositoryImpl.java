package br.com.marco.cadeacerva.sales.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;

import java.util.List;

@RequiredArgsConstructor
public class SaleCriteriaRepositoryImpl implements SaleCriteriaRepository {

    private final MongoTemplate template;

    @Override
    public Page<Sale> findBy(final SaleSearchCriteriaWrapper criteria, final Pageable pageable) {
        Query countQuert = criteria.createQuery();
        Query query = Query.of(countQuert).with(pageable);
        List<Sale> result = template.find(query, Sale.class);
        return PageableExecutionUtils.getPage(result, pageable, () -> template.count(countQuert, Sale.class));
    }
}
