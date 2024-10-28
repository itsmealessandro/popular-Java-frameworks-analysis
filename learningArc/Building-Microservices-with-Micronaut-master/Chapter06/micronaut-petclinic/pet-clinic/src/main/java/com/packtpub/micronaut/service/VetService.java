package com.packtpub.micronaut.service;

import com.packtpub.micronaut.service.dto.VetDTO;

import java.util.Collection;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.packtpub.micronaut.domain.Vet}.
 */
public interface VetService {

    /**
     * Save a vet.
     *
     * @param vetDTO the entity to save.
     * @return the persisted entity.
     */
    VetDTO save(VetDTO vetDTO) throws Exception;

    /**
     * Get all the vets.
     *
     * @return the list of entities.
     */
    Collection<VetDTO> findAll() throws Exception;


    /**
     * Get the "id" vet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VetDTO> findOne(Long id) throws Exception;

    /**
     * Delete the "id" vet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id) throws Exception;

    /**
     * Update average rating for a vet
     *
     * @param id
     * @param rating
     */
    void updateVetAverageRating(Long id, Double rating) throws Exception;
}
