package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.schedule.entity.Schedule;
import knk.erp.api.shlee.schedule.entity.Vacation;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class RAS {//RectifyAttendanceSpecification

    public static Specification<RectifyAttendance> id(Long id){//deleteFalse
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
    
    public static Specification<RectifyAttendance> delFalse(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<RectifyAttendance> mid(String mid){//memberId
        return (root, query, builder) -> {

            Join<Schedule, Member> sm = root.join("author");
            return builder.equal(sm.get("memberId"), mid);
        };
    }
    public static Specification<RectifyAttendance> did(Long departmentId){//memberId
        return (root, query, builder) -> {

            Join<Schedule, Member> sm = root.join("author");

            return builder.equal(sm.get("department").get("id"), departmentId);

        };
    }

    public static Specification<RectifyAttendance> approve1Is(boolean t){//approval1 is
        return (root, query, builder) -> builder.equal(root.get("approval1"), t);
    }


}
