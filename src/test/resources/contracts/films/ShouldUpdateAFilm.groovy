package contracts.films

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "should update a film"

    request {
        url $(regex("/api/films/" + positiveInt()))
        method PATCH()
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
        status OK()
        headers {
            contentType(applicationJson())
        }
        body([
                id: $(1),
                title: $("Call of the wild"),
                synopsis: $("A vibrant story of Buck, a big and kindhearted dog.")
              ])
    }
}
