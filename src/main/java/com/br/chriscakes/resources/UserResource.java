package com.br.chriscakes.resources;

import com.br.chriscakes.domain.dto.UserDto;
import com.br.chriscakes.domain.dto.UserInsertDto;
import com.br.chriscakes.domain.dto.UserUpdateDto;
import com.br.chriscakes.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    private UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        Page<UserDto> list = service.findAll(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        UserDto dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDto> insert(@Valid @RequestBody UserInsertDto dto) {
        UserDto newDto = service.insert(dto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserUpdateDto dto, @PathVariable Long id) {
        UserDto newdto = service.update(dto, id);
        return ResponseEntity.ok().body(newdto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
