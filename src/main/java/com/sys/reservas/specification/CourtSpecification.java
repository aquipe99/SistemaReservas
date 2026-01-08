package com.sys.reservas.specification;

import com.sys.reservas.entity.Court;
import org.springframework.data.jpa.domain.Specification;

public class CourtSpecification {

    public  static Specification<Court> globalFilter(String filter){
        return (root,query,cb)->{
            if(filter==null || filter.isEmpty()){
                return cb.conjunction();
            }
            String like = "%" + filter.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")),like);
        };
    }
}
