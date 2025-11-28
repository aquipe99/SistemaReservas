package com.sys.reservas.specification;

import com.sys.reservas.entity.Role;
import org.springframework.data.jpa.domain.Specification;

public class RoleSpecification {
    public static Specification<Role> hasNameLike(String name) {
        return (root, query, cb) ->
                name == null || name.trim().isEmpty()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Role> hasDescriptionLike(String description) {
        return (root, query, cb) ->
                description == null || description.trim().isEmpty()
                        ? cb.conjunction()
                        : cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }
}
