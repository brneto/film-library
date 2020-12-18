package contracts.films

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "should return a list of films"

    request {
        url "/api/films"
        method GET()
        headers {
            accept(applicationJson())
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([[
                id: $(anyPositiveInt()),
                title: $(consumer(any()), producer("Call of the wild")),
                synopsis: $(consumer(any()), producer("A vibrant story of Buck, a big and kindhearted dog."))
              ]])
    }
}
