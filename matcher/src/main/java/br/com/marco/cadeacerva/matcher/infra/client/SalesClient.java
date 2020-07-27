package br.com.marco.cadeacerva.matcher.infra.client;

import br.com.marco.cadeacerva.matcher.infra.client.dto.SaleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("sales")
public interface SalesClient {

    @RequestMapping(value = "/sale", consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<SaleDTO> searchSales(@RequestParam("search") String search, Pageable pageable);
}
