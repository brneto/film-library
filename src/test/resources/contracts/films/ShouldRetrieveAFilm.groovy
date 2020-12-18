package contracts.films

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "should return one film"

    request {
        url $(regex("/api/films/" + positiveInt()))
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
        body([
                id: $(1),
                title: $("Call of the wild"),
                synopsis: $("A vibrant story of Buck, a big and kindhearted dog.")
              ])
    }
}
