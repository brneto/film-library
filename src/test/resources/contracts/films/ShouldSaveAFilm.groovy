package contracts.films

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "should save a film"

    request {
        url "/api/films"
        method POST()
        headers {
            accept(applicationJson())
            contentType(applicationJson())
        }
        body([
                title: $("Call of the wild"),
                synopsis: $("A vibrant story of Buck, a big and kindhearted dog.")
        ])
    }

    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body([
                id: $(anyPositiveInt()),
                title: $("Call of the wild"),
                synopsis: $("A vibrant story of Buck, a big and kindhearted dog.")
              ])
    }
}
