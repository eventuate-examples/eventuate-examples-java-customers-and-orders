package contracts;

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/customers/1223232-none'
    }
    response {
        status 404
    }
}