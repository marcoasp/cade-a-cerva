package br.com.marco.cadeacerva.sales.endpoint;

import br.com.marco.cadeacerva.sales.domain.Sale;
import br.com.marco.cadeacerva.sales.domain.SaleRepository;
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
        return new SaleDTO(saleRepository.save(new Sale(newSale.getAddress(), newSale.getTags(), newSale.getLocation())));
    }

    @GetMapping
    public Page<SaleDTO> getSales(@RequestParam("location") double[] location, @RequestParam("distance") double distance, Pageable pageable) {
        Page<Sale> sales = saleRepository.findByLocationNear(
            new Point(location[0], location[1]),
            new Distance(distance, Metrics.KILOMETERS),
            pageable
        );
        return sales.map(SaleDTO::new);
    }
}
