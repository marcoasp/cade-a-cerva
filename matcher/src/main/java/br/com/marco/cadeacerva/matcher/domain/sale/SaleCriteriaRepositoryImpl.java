package br.com.marco.cadeacerva.matcher.domain.sale;

import com.google.common.collect.Lists;
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
    public List<Sale> findBy(final SaleSearchCriteriaWrapper criteria) {
        return template.aggregate(criteria.createQuery(), "sale", Sale.class).getMappedResults();
    }
}
