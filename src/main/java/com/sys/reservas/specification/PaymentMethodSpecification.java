package com.sys.reservas.specification;

import com.sys.reservas.entity.PaymentMethod;
import org.springframework.data.jpa.domain.Specification;

public class PaymentMethodSpecification {

    public static Specification<PaymentMethod> globalFilter(String filter){
        return (root,query,cb)->{
            if(filter==null || filter.isEmpty()){
                return cb.conjunction();
            }
            String like = "%" + filter.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")),like);
        };
    }
}
