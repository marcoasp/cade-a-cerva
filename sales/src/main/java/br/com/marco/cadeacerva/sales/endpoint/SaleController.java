package br.com.marco.cadeacerva.sales.endpoint;

import br.com.marco.cadeacerva.sales.domain.Sale;
import br.com.marco.cadeacerva.sales.domain.SaleRepository;
import br.com.marco.cadeacerva.sales.domain.SaleSearchCriteriaWrapper;
import br.com.marco.cadeacerva.sales.endpoint.dto.SaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleRepository saleRepository;

    @PostMapping
    public SaleDTO create(@RequestBody SaleDTO newSale) {
        return new SaleDTO(saleRepository.save(new Sale(newSale.getAddress(), newSale.getTags(), newSale.getPricePerLiter(), newSale.getLocation())));
    }

    @GetMapping
    public Page<SaleDTO> searchSales(@RequestParam("search") SaleSearchCriteriaWrapper search, Pageable pageable) {
        Page<Sale> sales = saleRepository.findBy(search, pageable);
        return sales.map(SaleDTO::new);
    }
}
