package example.micronaut;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Status;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Optional;

@Path("/pets") 
class PetResource {

    private final PetRepository petRepository;

    PetResource(PetRepository petRepository) { 
        this.petRepository = petRepository;
    }

    @GET 
    public List<NameDto> all() { 
        return petRepository.list();
    }

    @GET 
    @Path("/{name}") 
    public Optional<Pet> byName(@PathParam("name") String petsName) { 
        return petRepository.findByName(petsName);
    }

    @POST
    @Status(HttpStatus.CREATED)
    public void save(@NonNull @NotNull @Body PetSave petSave) {
        petRepository.save(petSave.getName(), petSave.getType());

    }
}
