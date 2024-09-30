package org.springframework.samples.petclinic.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.rest.dto.PetDto;
import org.springframework.samples.petclinic.rest.dto.PetFieldsDto;
import org.springframework.samples.petclinic.rest.dto.PetTypeDto;
import org.springframework.samples.petclinic.rest.dto.VisitDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class PetMapperImpl implements PetMapper {

    @Override
    public PetDto toPetDto(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetDto petDto = new PetDto();

        petDto.setOwnerId( petOwnerId( pet ) );
        petDto.setName( pet.getName() );
        petDto.setBirthDate( pet.getBirthDate() );
        petDto.setType( toPetTypeDto( pet.getType() ) );
        petDto.setId( pet.getId() );
        petDto.setVisits( visitListToVisitDtoList( pet.getVisits() ) );

        return petDto;
    }

    @Override
    public Collection<PetDto> toPetsDto(Collection<Pet> pets) {
        if ( pets == null ) {
            return null;
        }

        Collection<PetDto> collection = new ArrayList<PetDto>( pets.size() );
        for ( Pet pet : pets ) {
            collection.add( toPetDto( pet ) );
        }

        return collection;
    }

    @Override
    public Collection<Pet> toPets(Collection<PetDto> pets) {
        if ( pets == null ) {
            return null;
        }

        Collection<Pet> collection = new ArrayList<Pet>( pets.size() );
        for ( PetDto petDto : pets ) {
            collection.add( toPet( petDto ) );
        }

        return collection;
    }

    @Override
    public Pet toPet(PetDto petDto) {
        if ( petDto == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setId( petDto.getId() );
        pet.setName( petDto.getName() );
        pet.setBirthDate( petDto.getBirthDate() );
        pet.setType( toPetType( petDto.getType() ) );
        pet.setVisits( visitDtoListToVisitList( petDto.getVisits() ) );

        return pet;
    }

    @Override
    public Pet toPet(PetFieldsDto petFieldsDto) {
        if ( petFieldsDto == null ) {
            return null;
        }

        Pet pet = new Pet();

        pet.setName( petFieldsDto.getName() );
        pet.setBirthDate( petFieldsDto.getBirthDate() );
        pet.setType( toPetType( petFieldsDto.getType() ) );

        return pet;
    }

    @Override
    public PetTypeDto toPetTypeDto(PetType petType) {
        if ( petType == null ) {
            return null;
        }

        PetTypeDto petTypeDto = new PetTypeDto();

        petTypeDto.setName( petType.getName() );
        petTypeDto.setId( petType.getId() );

        return petTypeDto;
    }

    @Override
    public PetType toPetType(PetTypeDto petTypeDto) {
        if ( petTypeDto == null ) {
            return null;
        }

        PetType petType = new PetType();

        petType.setId( petTypeDto.getId() );
        petType.setName( petTypeDto.getName() );

        return petType;
    }

    @Override
    public Collection<PetTypeDto> toPetTypeDtos(Collection<PetType> petTypes) {
        if ( petTypes == null ) {
            return null;
        }

        Collection<PetTypeDto> collection = new ArrayList<PetTypeDto>( petTypes.size() );
        for ( PetType petType : petTypes ) {
            collection.add( toPetTypeDto( petType ) );
        }

        return collection;
    }

    private Integer petOwnerId(Pet pet) {
        if ( pet == null ) {
            return null;
        }
        Owner owner = pet.getOwner();
        if ( owner == null ) {
            return null;
        }
        Integer id = owner.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected VisitDto visitToVisitDto(Visit visit) {
        if ( visit == null ) {
            return null;
        }

        VisitDto visitDto = new VisitDto();

        visitDto.setDate( visit.getDate() );
        visitDto.setDescription( visit.getDescription() );
        visitDto.setId( visit.getId() );

        return visitDto;
    }

    protected List<VisitDto> visitListToVisitDtoList(List<Visit> list) {
        if ( list == null ) {
            return null;
        }

        List<VisitDto> list1 = new ArrayList<VisitDto>( list.size() );
        for ( Visit visit : list ) {
            list1.add( visitToVisitDto( visit ) );
        }

        return list1;
    }

    protected Visit visitDtoToVisit(VisitDto visitDto) {
        if ( visitDto == null ) {
            return null;
        }

        Visit visit = new Visit();

        visit.setId( visitDto.getId() );
        visit.setDate( visitDto.getDate() );
        visit.setDescription( visitDto.getDescription() );

        return visit;
    }

    protected List<Visit> visitDtoListToVisitList(List<VisitDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Visit> list1 = new ArrayList<Visit>( list.size() );
        for ( VisitDto visitDto : list ) {
            list1.add( visitDtoToVisit( visitDto ) );
        }

        return list1;
    }
}
