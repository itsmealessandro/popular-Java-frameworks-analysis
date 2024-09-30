package org.springframework.samples.petclinic.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.rest.dto.OwnerDto;
import org.springframework.samples.petclinic.rest.dto.OwnerFieldsDto;
import org.springframework.samples.petclinic.rest.dto.PetDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class OwnerMapperImpl implements OwnerMapper {

    @Autowired
    private PetMapper petMapper;

    @Override
    public OwnerDto toOwnerDto(Owner owner) {
        if ( owner == null ) {
            return null;
        }

        OwnerDto ownerDto = new OwnerDto();

        ownerDto.setFirstName( owner.getFirstName() );
        ownerDto.setLastName( owner.getLastName() );
        ownerDto.setAddress( owner.getAddress() );
        ownerDto.setCity( owner.getCity() );
        ownerDto.setTelephone( owner.getTelephone() );
        ownerDto.setId( owner.getId() );
        ownerDto.setPets( petListToPetDtoList( owner.getPets() ) );

        return ownerDto;
    }

    @Override
    public Owner toOwner(OwnerDto ownerDto) {
        if ( ownerDto == null ) {
            return null;
        }

        Owner owner = new Owner();

        owner.setId( ownerDto.getId() );
        owner.setFirstName( ownerDto.getFirstName() );
        owner.setLastName( ownerDto.getLastName() );
        owner.setAddress( ownerDto.getAddress() );
        owner.setCity( ownerDto.getCity() );
        owner.setTelephone( ownerDto.getTelephone() );
        owner.setPets( petDtoListToPetList( ownerDto.getPets() ) );

        return owner;
    }

    @Override
    public Owner toOwner(OwnerFieldsDto ownerDto) {
        if ( ownerDto == null ) {
            return null;
        }

        Owner owner = new Owner();

        owner.setFirstName( ownerDto.getFirstName() );
        owner.setLastName( ownerDto.getLastName() );
        owner.setAddress( ownerDto.getAddress() );
        owner.setCity( ownerDto.getCity() );
        owner.setTelephone( ownerDto.getTelephone() );

        return owner;
    }

    @Override
    public List<OwnerDto> toOwnerDtoCollection(Collection<Owner> ownerCollection) {
        if ( ownerCollection == null ) {
            return null;
        }

        List<OwnerDto> list = new ArrayList<OwnerDto>( ownerCollection.size() );
        for ( Owner owner : ownerCollection ) {
            list.add( toOwnerDto( owner ) );
        }

        return list;
    }

    @Override
    public Collection<Owner> toOwners(Collection<OwnerDto> ownerDtos) {
        if ( ownerDtos == null ) {
            return null;
        }

        Collection<Owner> collection = new ArrayList<Owner>( ownerDtos.size() );
        for ( OwnerDto ownerDto : ownerDtos ) {
            collection.add( toOwner( ownerDto ) );
        }

        return collection;
    }

    protected List<PetDto> petListToPetDtoList(List<Pet> list) {
        if ( list == null ) {
            return null;
        }

        List<PetDto> list1 = new ArrayList<PetDto>( list.size() );
        for ( Pet pet : list ) {
            list1.add( petMapper.toPetDto( pet ) );
        }

        return list1;
    }

    protected List<Pet> petDtoListToPetList(List<PetDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Pet> list1 = new ArrayList<Pet>( list.size() );
        for ( PetDto petDto : list ) {
            list1.add( petMapper.toPet( petDto ) );
        }

        return list1;
    }
}
