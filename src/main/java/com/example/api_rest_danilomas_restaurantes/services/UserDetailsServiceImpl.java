package com.example.api_rest_danilomas_restaurantes.services;

import com.example.api_rest_danilomas_restaurantes.config.PasswordEncoderConfig;
import com.example.api_rest_danilomas_restaurantes.repositories.UserEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserEntityRepository userRepository;

    UserDetailsServiceImpl(UserEntityRepository userRepository, PasswordEncoderConfig passwordEncoderConfig) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(username+" no encontrado")
        );
    }
}
