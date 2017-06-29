package contracts;

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/customers/1223232'
    }
    response {
        status 200
        headers {
            header('Content-Type': 'application/json;charset=UTF-8')
        }
        body("{}")
    }
}