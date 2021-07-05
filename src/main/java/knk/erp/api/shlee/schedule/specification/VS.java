package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VS {//VacationSpecification

    public static Specification<Vacation> delFalse(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<Vacation> mid(String mid){//memberId
        return ((root, query, builder) -> {
            Join<Schedule, Member> sm = root.join("author");
            return builder.equal(sm.get("memberId"), mid);
        });
    }

    public static Specification<Vacation> did(Long did){//memberId
        return (root, query, builder) -> {

            Join<Schedule, Member> sm = root.join("author");

            return builder.equal(sm.get("department").get("id"), did);

        };
    }

    public static Specification<Vacation> rejectIs(boolean t){//approval1 is
        return (root, query, builder) -> builder.equal(root.get("reject"), t);
    }

    public static Specification<Vacation> approve1Is(boolean t){//approval1 is
        return (root, query, builder) -> builder.equal(root.get("approval1"), t);
    }

    public static Specification<Vacation> approve2Is(boolean t){//approval2 is
        return (root, query, builder) -> builder.equal(root.get("approval2"), t);
    }

    public static Specification<Vacation> targetDateBetween(LocalDateTime td){//startDate after
        return (root, query, builder) -> builder.between(builder.literal(td),
                root.get("startDate"),
                root.get("endDate"));
    }
    public static Specification<Vacation> vacationDateBetween(LocalDate sd, LocalDate ed){
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThanOrEqualTo(root.get("endDate"), sd));
            predicates.add(builder.lessThanOrEqualTo(root.get("startDate"), ed));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
