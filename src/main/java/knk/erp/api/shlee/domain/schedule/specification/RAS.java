package knk.erp.api.shlee.domain.schedule.specification;


import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.schedule.entity.Attendance;
import knk.erp.api.shlee.domain.schedule.entity.RectifyAttendance;
import knk.erp.api.shlee.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RAS {//RectifyAttendanceSpecification


    public static Specification<RectifyAttendance> searchWithDateAndMemberId(LocalDate today, String memberId){
        return (root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithDateMemberId(today, memberId, root, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> getPredicateWithDateMemberId(LocalDate today, String memberId, Root<RectifyAttendance> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();
        //삭제되지 않았고
        predicate.add(builder.isFalse(root.get("deleted")));
        //오늘 일자이며
        predicate.add(builder.equal(root.get("attendanceDate"), today));
        //작성자
        Join<Schedule, Member> sm = root.join("author");
        predicate.add(builder.equal(sm.get("memberId"), memberId));

        return predicate;
    }

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

    public static Specification<RectifyAttendance> attendanceDate(LocalDate td){//approval1 is
        return (root, query, builder) -> builder.equal(root.get("attendanceDate"), td);
    }


}
