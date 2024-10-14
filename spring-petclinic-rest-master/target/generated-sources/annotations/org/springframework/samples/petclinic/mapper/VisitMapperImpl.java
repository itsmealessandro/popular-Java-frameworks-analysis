package org.springframework.samples.petclinic.mapper;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.rest.dto.VisitDto;
import org.springframework.samples.petclinic.rest.dto.VisitFieldsDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class VisitMapperImpl implements VisitMapper {

    @Override
    public Visit toVisit(VisitDto visitDto) {
        if ( visitDto == null ) {
            return null;
        }

        Visit visit = new Visit();

        visit.setId( visitDto.getId() );
        visit.setDate( visitDto.getDate() );
        visit.setDescription( visitDto.getDescription() );

        return visit;
    }

    @Override
    public Visit toVisit(VisitFieldsDto visitFieldsDto) {
        if ( visitFieldsDto == null ) {
            return null;
        }

        Visit visit = new Visit();

        visit.setDate( visitFieldsDto.getDate() );
        visit.setDescription( visitFieldsDto.getDescription() );

        return visit;
    }

    @Override
    public VisitDto toVisitDto(Visit visit) {
        if ( visit == null ) {
            return null;
        }

        VisitDto visitDto = new VisitDto();

        visitDto.setPetId( visitPetId( visit ) );
        visitDto.setDate( visit.getDate() );
        visitDto.setDescription( visit.getDescription() );
        visitDto.setId( visit.getId() );

        return visitDto;
    }

    @Override
    public Collection<VisitDto> toVisitsDto(Collection<Visit> visits) {
        if ( visits == null ) {
            return null;
        }

        Collection<VisitDto> collection = new ArrayList<VisitDto>( visits.size() );
        for ( Visit visit : visits ) {
            collection.add( toVisitDto( visit ) );
        }

        return collection;
    }

    private Integer visitPetId(Visit visit) {
        if ( visit == null ) {
            return null;
        }
        Pet pet = visit.getPet();
        if ( pet == null ) {
            return null;
        }
        Integer id = pet.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
