package br.com.marco.cadeacerva.sales.domain;

import br.com.marco.cadeacerva.sales.endpoint.dto.SaleDTO;
import br.com.marco.cadeacerva.testcommons.utils.annotation.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

import static br.com.marco.cadeacerva.testcommons.utils.JsonPayloadProvider.from;
import static net.javacrumbs.jsonunit.JsonMatchers.jsonEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@IntegrationTest
public class SaleProducerTest {

    @Autowired
    SaleProducer saleProducer;

    @Autowired
    @Qualifier("sales-out-0")
    MessageChannel outputDestination;

    @Autowired
    MessageCollector collector;

    @Test
    public void shouldProduceSaleMessage() throws IOException {
        saleProducer.produce(SaleDTO.from(new Sale("address", Arrays.asList("tag1", "tag2"), 10.0, new double[]{20.0, 30.0})));
        BlockingQueue<Message<?>> queue = collector.forChannel(outputDestination);
        assertThat(queue, receivesPayloadThat(jsonEquals(from(this.getClass(), "shouldProduceSaleMessage"))));
    }
}