package org.heapminds.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.heapminds.models.Role;
import org.heapminds.models.UserEntity;
import org.heapminds.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomDetailUserService implements UserDetailsService{

    private final UserRepository userRepository;

    CustomDetailUserService(UserRepository userRepository){
        this.userRepository =userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username not found"));
        return new User(userEntity.getUsername(), userEntity.getPassword(), mapToGrantedAuthorities(userEntity.getRoles()));
    }

    private Collection<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    }
    
}
