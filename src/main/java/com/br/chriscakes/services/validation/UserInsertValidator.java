package com.br.chriscakes.services.validation;

import com.br.chriscakes.domain.dto.UserInsertDto;
import com.br.chriscakes.repositories.UserRepository;
import com.br.chriscakes.resources.exceptions.FieldMessage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDto> {

    private UserRepository repository;

    public UserInsertValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(UserInsertValid ann) {
        //Override method
    }

    @Override
    public boolean isValid(UserInsertDto dto, ConstraintValidatorContext context) {

        List<FieldMessage> list = new ArrayList<>();

        var user = repository.findByEmail(dto.getEmail());

        if (user != null) {
            list.add(new FieldMessage("email", "Email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
