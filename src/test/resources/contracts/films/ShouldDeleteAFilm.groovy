package contracts.films

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description "should return a list of films"

    request {
        url $(regex("/api/films/" + positiveInt()))
        method DELETE()
    }

    response {
        status NO_CONTENT()
    }
}
