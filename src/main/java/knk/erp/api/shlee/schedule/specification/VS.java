package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class VS {//VacationSpecification

    public static Specification<Vacation> df(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<Vacation> mid(String memberId){//memberId
        return (root, query, builder) -> builder.equal(root.get("memberId"), memberId);
    }

    public static Specification<Vacation> did(Long departmentId){//memberId
        return (root, query, builder) -> builder.equal(root.get("departmentId"), departmentId);
    }

    public static Specification<Vacation> a1is(boolean t){//approval1 is
        return (root, query, builder) -> builder.equal(root.get("approval1"), t);
    }

    public static Specification<Vacation> a2is(boolean t){//approval2 is
        return (root, query, builder) -> builder.equal(root.get("approval2"), t);
    }
    public static Specification<Vacation> sda(LocalDateTime td){//startDate after
        return (root, query, builder) -> builder.lessThan(root.get("startDate"), td);
    }
    public static Specification<Vacation> edb(LocalDateTime td){//endDate before
        return (root, query, builder) -> builder.greaterThan(root.get("endDate"), td);
    }

}
