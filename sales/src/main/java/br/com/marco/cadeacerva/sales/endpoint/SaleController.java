package br.com.marco.cadeacerva.sales.endpoint;

import br.com.marco.cadeacerva.sales.domain.Sale;
import br.com.marco.cadeacerva.sales.domain.SaleRepository;
import br.com.marco.cadeacerva.sales.endpoint.dto.SaleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {

    private final SaleRepository saleRepository;

    @PostMapping
    public SaleDTO create(@RequestBody SaleDTO newSale) {
        return new SaleDTO(saleRepository.save(new Sale(newSale.getAddress(), newSale.getTags(), newSale.getLocation())));
    }
}
