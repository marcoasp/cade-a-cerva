import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'PUT'
        urlPath('/user/existing-user@email.com')
        headers {
            contentType(applicationJson())
        }
        body '''{ "email": "existing-user@email.com" }'''
    }

    response {
        status 200
        body '''{ "email": "existing-user@email.com" }'''
        headers {
            contentType(applicationJson())
        }
    }
}