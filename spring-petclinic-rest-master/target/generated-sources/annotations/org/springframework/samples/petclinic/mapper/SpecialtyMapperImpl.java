package org.springframework.samples.petclinic.mapper;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.processing.Generated;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.rest.dto.SpecialtyDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class SpecialtyMapperImpl implements SpecialtyMapper {

    @Override
    public Specialty toSpecialty(SpecialtyDto specialtyDto) {
        if ( specialtyDto == null ) {
            return null;
        }

        Specialty specialty = new Specialty();

        specialty.setId( specialtyDto.getId() );
        specialty.setName( specialtyDto.getName() );

        return specialty;
    }

    @Override
    public SpecialtyDto toSpecialtyDto(Specialty specialty) {
        if ( specialty == null ) {
            return null;
        }

        SpecialtyDto specialtyDto = new SpecialtyDto();

        specialtyDto.setId( specialty.getId() );
        specialtyDto.setName( specialty.getName() );

        return specialtyDto;
    }

    @Override
    public Collection<SpecialtyDto> toSpecialtyDtos(Collection<Specialty> specialties) {
        if ( specialties == null ) {
            return null;
        }

        Collection<SpecialtyDto> collection = new ArrayList<SpecialtyDto>( specialties.size() );
        for ( Specialty specialty : specialties ) {
            collection.add( toSpecialtyDto( specialty ) );
        }

        return collection;
    }

    @Override
    public Collection<Specialty> toSpecialtys(Collection<SpecialtyDto> specialties) {
        if ( specialties == null ) {
            return null;
        }

        Collection<Specialty> collection = new ArrayList<Specialty>( specialties.size() );
        for ( SpecialtyDto specialtyDto : specialties ) {
            collection.add( toSpecialty( specialtyDto ) );
        }

        return collection;
    }
}
