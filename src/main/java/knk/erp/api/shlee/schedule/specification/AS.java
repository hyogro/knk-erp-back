package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.schedule.entity.Attendance;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AS {//AttendanceSpecification

    public static Specification<Attendance> id(Long id){//deleteFalse
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }

    public static Specification<Attendance> df(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<Attendance> mid(String memberId){//memberId
        return (root, query, builder) -> builder.equal(root.get("memberId"), memberId);
    }

    public static Specification<Attendance> ad(LocalDate t){//memberId
        return (root, query, builder) -> builder.equal(root.get("attendanceDate"), t);
    }


    public static Specification<Attendance> did(Long departmentId){//memberId
        return (root, query, builder) -> builder.equal(root.get("departmentId"), departmentId);
    }

    public static Specification<Attendance> ona(LocalTime td){//startDate after
        return (root, query, builder) -> builder.greaterThan(root.get("onWork"), td);
    }
    public static Specification<Attendance> ofb(LocalTime td){//endDate before
        return (root, query, builder) -> builder.lessThan(root.get("offWork"), td);
    }

}
