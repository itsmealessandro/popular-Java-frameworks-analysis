package org.springframework.samples.petclinic.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.rest.dto.PetTypeDto;
import org.springframework.samples.petclinic.rest.dto.PetTypeFieldsDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class PetTypeMapperImpl implements PetTypeMapper {

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
    public PetType toPetType(PetTypeFieldsDto petTypeFieldsDto) {
        if ( petTypeFieldsDto == null ) {
            return null;
        }

        PetType petType = new PetType();

        petType.setName( petTypeFieldsDto.getName() );

        return petType;
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
    public PetTypeFieldsDto toPetTypeFieldsDto(PetType petType) {
        if ( petType == null ) {
            return null;
        }

        PetTypeFieldsDto petTypeFieldsDto = new PetTypeFieldsDto();

        petTypeFieldsDto.setName( petType.getName() );

        return petTypeFieldsDto;
    }

    @Override
    public List<PetTypeDto> toPetTypeDtos(Collection<PetType> petTypes) {
        if ( petTypes == null ) {
            return null;
        }

        List<PetTypeDto> list = new ArrayList<PetTypeDto>( petTypes.size() );
        for ( PetType petType : petTypes ) {
            list.add( toPetTypeDto( petType ) );
        }

        return list;
    }
}
