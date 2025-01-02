package com.hub.foro.api.modules.shared.validation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    private Class<?> entity;
    private String field;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.entity = constraintAnnotation.entity();
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
         if (value == null) {
            return true; // Permite valores nulos si no hay restricciones adicionales
        }

        Long count = entityManager.createQuery(
                        "SELECT COUNT(e) FROM " + entity.getName() + " e WHERE e." + field + " = :value", Long.class)
                .setParameter("value", value)
                .getSingleResult();

         // Si el valor ya existe, se agrega el mensaje de error personalizado.
         if (count > 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The {field} already exists")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
    
}
