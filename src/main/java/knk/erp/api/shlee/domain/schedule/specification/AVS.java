package knk.erp.api.shlee.domain.schedule.specification;


import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.schedule.entity.AddVacation;
import knk.erp.api.shlee.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class AVS {//VacationSpecification

    public static Specification<AddVacation> delFalse(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<AddVacation> mid(String mid){//memberId
        return ((root, query, builder) -> {
            Join<Schedule, Member> sm = root.join("receiver");
            return builder.equal(sm.get("memberId"), mid);
        });
    }

}
