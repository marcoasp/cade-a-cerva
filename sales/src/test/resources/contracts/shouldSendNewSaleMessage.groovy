package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label "sendNewSaleMessage"
    input {
        triggeredBy("sendNewSaleMessageTriggered()")
    }
    outputMessage {
        sentTo"sales"
        body '''{ "address": "address", "tags": ["tag1", "tag2"], "pricePerLiter": 10.0, "location": [20.0, 30.0]}'''
    }
}

