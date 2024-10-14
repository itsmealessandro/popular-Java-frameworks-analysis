package org.springframework.samples.petclinic.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.samples.petclinic.model.Role;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.rest.dto.RoleDto;
import org.springframework.samples.petclinic.rest.dto.UserDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public Role toRole(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        Role role = new Role();

        role.setName( roleDto.getName() );

        return role;
    }

    @Override
    public RoleDto toRoleDto(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleDto roleDto = new RoleDto();

        roleDto.setName( role.getName() );

        return roleDto;
    }

    @Override
    public Collection<RoleDto> toRoleDtos(Collection<Role> roles) {
        if ( roles == null ) {
            return null;
        }

        Collection<RoleDto> collection = new ArrayList<RoleDto>( roles.size() );
        for ( Role role : roles ) {
            collection.add( toRoleDto( role ) );
        }

        return collection;
    }

    @Override
    public User toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDto.getUsername() );
        user.setPassword( userDto.getPassword() );
        user.setEnabled( userDto.getEnabled() );
        user.setRoles( roleDtoListToRoleSet( userDto.getRoles() ) );

        return user;
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername( user.getUsername() );
        userDto.setPassword( user.getPassword() );
        userDto.setEnabled( user.getEnabled() );
        userDto.setRoles( roleSetToRoleDtoList( user.getRoles() ) );

        return userDto;
    }

    @Override
    public Collection<Role> toRoles(Collection<RoleDto> roleDtos) {
        if ( roleDtos == null ) {
            return null;
        }

        Collection<Role> collection = new ArrayList<Role>( roleDtos.size() );
        for ( RoleDto roleDto : roleDtos ) {
            collection.add( toRole( roleDto ) );
        }

        return collection;
    }

    protected Set<Role> roleDtoListToRoleSet(List<RoleDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<Role> set = new HashSet<Role>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( RoleDto roleDto : list ) {
            set.add( toRole( roleDto ) );
        }

        return set;
    }

    protected List<RoleDto> roleSetToRoleDtoList(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        List<RoleDto> list = new ArrayList<RoleDto>( set.size() );
        for ( Role role : set ) {
            list.add( toRoleDto( role ) );
        }

        return list;
    }
}
