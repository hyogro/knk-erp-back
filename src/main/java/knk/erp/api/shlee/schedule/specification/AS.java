package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    public static Specification<Attendance> onWorkAfter(LocalTime td){//startDate after
        return (root, query, builder) -> builder.greaterThan(root.get("onWork"), td);
    }
    public static Specification<Attendance> offWorkBefore(LocalTime td){//endDate before
        return (root, query, builder) -> builder.lessThan(root.get("offWork"), td);
    }

    public static Specification<Attendance> startDateAfter(LocalDateTime td){//startDate after
        return (root, query, builder) -> builder.greaterThan(root.get("startDate"), td);
    }
    public static Specification<Attendance> endDateBefore(LocalDateTime td){//endDate before
        return (root, query, builder) -> builder.lessThan(root.get("endDate"), td);
    }
}
