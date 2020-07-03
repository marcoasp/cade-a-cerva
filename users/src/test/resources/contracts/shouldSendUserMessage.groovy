import org.springframework.cloud.contract.spec.Contract

Contract.make {
    label "sendUserMessage"
    input {
        triggeredBy("sendUserMessageTriggered()")
    }
    outputMessage {
        sentTo"users-out-0"
        body '''{ "email": "existing-user@email.com", "location": [10.0, 20.5], "area": 3.5 }'''
    }
}