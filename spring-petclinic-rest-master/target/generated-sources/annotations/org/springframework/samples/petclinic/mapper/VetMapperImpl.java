package org.springframework.samples.petclinic.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.rest.dto.SpecialtyDto;
import org.springframework.samples.petclinic.rest.dto.VetDto;
import org.springframework.samples.petclinic.rest.dto.VetFieldsDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class VetMapperImpl implements VetMapper {

    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Override
    public Vet toVet(VetDto vetDto) {
        if ( vetDto == null ) {
            return null;
        }

        Vet vet = new Vet();

        vet.setId( vetDto.getId() );
        vet.setFirstName( vetDto.getFirstName() );
        vet.setLastName( vetDto.getLastName() );
        vet.setSpecialties( specialtyDtoListToSpecialtyList( vetDto.getSpecialties() ) );

        return vet;
    }

    @Override
    public Vet toVet(VetFieldsDto vetFieldsDto) {
        if ( vetFieldsDto == null ) {
            return null;
        }

        Vet vet = new Vet();

        vet.setFirstName( vetFieldsDto.getFirstName() );
        vet.setLastName( vetFieldsDto.getLastName() );
        vet.setSpecialties( specialtyDtoListToSpecialtyList( vetFieldsDto.getSpecialties() ) );

        return vet;
    }

    @Override
    public VetDto toVetDto(Vet vet) {
        if ( vet == null ) {
            return null;
        }

        VetDto vetDto = new VetDto();

        vetDto.setFirstName( vet.getFirstName() );
        vetDto.setLastName( vet.getLastName() );
        vetDto.setSpecialties( specialtyListToSpecialtyDtoList( vet.getSpecialties() ) );
        vetDto.setId( vet.getId() );

        return vetDto;
    }

    @Override
    public Collection<VetDto> toVetDtos(Collection<Vet> vets) {
        if ( vets == null ) {
            return null;
        }

        Collection<VetDto> collection = new ArrayList<VetDto>( vets.size() );
        for ( Vet vet : vets ) {
            collection.add( toVetDto( vet ) );
        }

        return collection;
    }

    protected List<Specialty> specialtyDtoListToSpecialtyList(List<SpecialtyDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Specialty> list1 = new ArrayList<Specialty>( list.size() );
        for ( SpecialtyDto specialtyDto : list ) {
            list1.add( specialtyMapper.toSpecialty( specialtyDto ) );
        }

        return list1;
    }

    protected List<SpecialtyDto> specialtyListToSpecialtyDtoList(List<Specialty> list) {
        if ( list == null ) {
            return null;
        }

        List<SpecialtyDto> list1 = new ArrayList<SpecialtyDto>( list.size() );
        for ( Specialty specialty : list ) {
            list1.add( specialtyMapper.toSpecialtyDto( specialty ) );
        }

        return list1;
    }
}
