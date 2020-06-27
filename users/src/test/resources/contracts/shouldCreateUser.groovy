import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'PUT'

        urlPath $(consumer(regex('^/user/[a-zA-Z0-9._%+-]+(@|%40)[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}')), producer('/user/existing-user@email.com'))
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