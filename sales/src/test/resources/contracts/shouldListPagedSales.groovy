package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        urlPath ('/sale') {
            queryParameters {
                parameter 'search': 'location=10.01,10.02:distance=10.05:tags=abc,cde,dce:pricePerLiter=50.0'
                parameter 'page': '0'
                parameter 'size': '1'
            }
        }
        headers {
            contentType(applicationJson())
        }
    }

    response {
        status 200
        body '''{
            "content": [
                {
                    "id": null,
                    "address": "address",
                    "tags": [
                        "tag1",
                        "tag2"
                    ],
                    "pricePerLiter": 10.0,
                    "location": [
                        10.0,
                        20.0
                    ]
                }
            ],
            "pageable": {
                "sort": {
                    "sorted": false,
                    "unsorted": true,
                    "empty": true
                },
                "pageNumber": 0,
                "pageSize": 1,
                "offset": 0,
                "paged": true,
                "unpaged": false
            },
            "totalElements": 2,
            "last": false,
            "totalPages": 2,
            "numberOfElements": 1,
            "sort": {
                "sorted": false,
                "unsorted": true,
                "empty": true
            },
            "first": true,
            "size": 1,
            "number": 0,
            "empty": false
        }'''
        headers {
            contentType(applicationJson())
        }
    }
}