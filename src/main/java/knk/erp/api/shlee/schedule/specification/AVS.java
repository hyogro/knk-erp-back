package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.AddVacation;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
