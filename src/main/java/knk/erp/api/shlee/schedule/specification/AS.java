package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AS {//AttendanceSpecification

    public static Specification<Attendance> id(Long id){//deleteFalse
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }

    public static Specification<Attendance> delFalse(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<Attendance> mid(String mid){//memberId
        return ((root, query, builder) -> {
            Join<Schedule, Member> sm = root.join("author");
            return builder.equal(sm.get("memberId"), mid);
        });
    }

    public static Specification<Attendance> atteDate(LocalDate t){//memberId
        return (root, query, builder) -> builder.equal(root.get("attendanceDate"), t);
    }


    public static Specification<Attendance> did(Long did){//memberId
        return (root, query, builder) -> {

            Join<Schedule, Member> sm = root.join("author");

            return builder.equal(sm.get("department").get("id"), did);

        };
    }

    public static Specification<Attendance> attendanceDateBetween(LocalDate sd, LocalDate ed){
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThan(root.get("attendanceDate"), sd));
            predicates.add(builder.lessThan(root.get("attendanceDate"), ed));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<Attendance> offWorked(){
        return (root, query, builder) -> builder.isNotNull(root.get("offWork"));
    }

}
