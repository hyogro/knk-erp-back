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

public class AS {//AttendanceSpecification

    /**날짜와 맴버아이디로 조회**/
    public static Specification<Attendance> searchWithDateAndMemberId(LocalDate date, String memberId){
        return (root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithDateAndMemberIdAndNotDeleted(date, memberId, root, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        };
    }
    private static List<Predicate> getPredicateWithDateAndMemberIdAndNotDeleted(LocalDate date, String memberId, Root<Attendance> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();
        //삭제되지 않았고
        predicate.add(builder.isFalse(root.get("deleted")));

        //오늘 일자이며
        predicate.add(builder.equal(root.get("attendanceDate"), date));

        //작성자
        predicate.add(withMemberId(memberId, root, builder));
        predicate.add(withMemberDF(memberId, root, builder));

        return predicate;
    }


    /**날짜범위로 조회**/
    public static Specification<Attendance> searchWithDateBetween(String memberId, LocalDate startDate, LocalDate endDate){
        return (root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithDateBetweenAndNotDeleted(memberId, startDate, endDate, root, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        };
    }
    private static List<Predicate> getPredicateWithDateBetweenAndNotDeleted(String memberId, LocalDate startDate, LocalDate endDate, Root<Attendance> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();
        //삭제되지 않았고
        predicate.add(builder.isFalse(root.get("deleted")));

        //시작일보다 같거나 크며
        predicate.add(builder.greaterThanOrEqualTo(root.get("attendanceDate"), startDate));

        //종료일보다 같거나 작을때
        predicate.add(builder.lessThanOrEqualTo(root.get("attendanceDate"), endDate));

        //작성자
        predicate.add(withMemberId(memberId, root, builder));
        predicate.add(withMemberDF(memberId, root, builder));

        return predicate;
    }

    /**근태 아이디로 조회*/
    public static Specification<Attendance> searchWithAttendanceId(Long attendanceId){
        return (root, query, builder) -> {
            List<Predicate> predicate = getPredicateWithAttendanceId(attendanceId, root, builder);
            return builder.and(predicate.toArray(new Predicate[0]));
        };
    }

    private static List<Predicate> getPredicateWithAttendanceId(Long attendanceId, Root<Attendance> root, CriteriaBuilder builder) {
        List<Predicate> predicate = new ArrayList<>();
        //삭제되지 않았고
        predicate.add(builder.isFalse(root.get("deleted")));
        //id가 일치하는 경우
        predicate.add(builder.equal(root.get("id"), attendanceId));

        return predicate;
    }





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
    public static Specification<Attendance> memberDF(){//memberId
        return (root, query, builder) -> {

            Join<Schedule, Member> sm = root.join("author");

            return builder.isFalse(sm.get("deleted"));
        };
    }

    public static Specification<Attendance> attendanceDateBetween(LocalDate sd, LocalDate ed){
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.greaterThanOrEqualTo(root.get("attendanceDate"), sd));
            predicates.add(builder.lessThanOrEqualTo(root.get("attendanceDate"), ed));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Attendance> offWorked(){
        return (root, query, builder) -> builder.isNotNull(root.get("offWork"));
    }


    private static Predicate withMemberId(String memberId, Root<Attendance> root, CriteriaBuilder builder){
        Join<Schedule, Member> sm = root.join("author");
        return builder.equal(sm.get("memberId"), memberId);
    }

    private static Predicate withMemberDF(String memberId, Root<Attendance> root, CriteriaBuilder builder){
        Join<Schedule, Member> sm = root.join("author");
        return builder.isFalse(sm.get("deleted"));
    }

}
