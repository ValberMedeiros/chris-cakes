package com.br.chriscakes.services;

import com.br.chriscakes.domain.dto.RoleDto;
import com.br.chriscakes.domain.dto.UserDto;
import com.br.chriscakes.domain.dto.UserInsertDto;
import com.br.chriscakes.domain.dto.UserUpdateDto;
import com.br.chriscakes.domain.entities.User;
import com.br.chriscakes.repositories.RoleRepository;
import com.br.chriscakes.repositories.UserRepository;
import com.br.chriscakes.services.exceptions.DataBaseException;
import com.br.chriscakes.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> list =  repository.findAll(pageable);
        return list
                .map(UserDto::new);
    }

    public UserDto findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.map(UserDto::new).orElseThrow(() -> {
            throw new ResourceNotFoundException("Entity not found.");
        });
    }

    @Transactional
    public UserDto insert(UserInsertDto dto) {
        var entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return new UserDto(entity);
    }

    @Transactional
    public UserDto update(UserUpdateDto dto, Long id) {
        try {
            User entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(UserDto dto, User entity) {

        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDto roleDto : dto.getRoles()) {
            var role = roleRepository.getOne(roleDto.getId());
            entity.getRoles().add(role);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByEmail(username);
        if(user == null) {
            logger.error("User not found: {}", username);
            throw new UsernameNotFoundException("EMail not found");
        }
        logger.info("User found: {}", username);
        return user;
    }
}
