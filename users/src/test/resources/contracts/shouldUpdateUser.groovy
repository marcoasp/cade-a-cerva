package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'PUT'
        urlPath('/user/me')
        headers {
            contentType(applicationJson())
        }
        body '''{ "email": "existing-user@email.com", "location": [10.0, 20.5], "area": 3.5 }'''
    }
    response {
        status 200
        body '''{ "email": "existing-user@email.com", "location": [10.0, 20.5], "area": 3.5 }'''
        headers {
            contentType(applicationJson())
        }
    }
}
