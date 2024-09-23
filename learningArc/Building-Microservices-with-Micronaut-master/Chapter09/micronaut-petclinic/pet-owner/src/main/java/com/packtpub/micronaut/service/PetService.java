package com.packtpub.micronaut.service;

import com.packtpub.micronaut.service.dto.PetDTO;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.packtpub.micronaut.domain.Pet}.
 */
public interface PetService {

    /**
     * Save a pet.
     *
     * @param petDTO the entity to save.
     * @return the persisted entity.
     */
    PetDTO save(PetDTO petDTO);

    /**
     * Get all the pets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PetDTO> findAll(Pageable pageable);


    /**
     * Get the "id" pet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PetDTO> findOne(Long id);

    /**
     * Delete the "id" pet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
