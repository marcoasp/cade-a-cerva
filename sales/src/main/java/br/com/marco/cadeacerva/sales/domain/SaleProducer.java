package br.com.marco.cadeacerva.sales.domain;

import br.com.marco.cadeacerva.sales.endpoint.dto.SaleDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaleProducer {

    private final StreamBridge streamBridge;

    public void produce(SaleDTO sale) {
        log.info("Producing Sale: {}", sale.toString());
        streamBridge.send("sales-out-0", MessageBuilder.withPayload(sale).build());
    }
}
