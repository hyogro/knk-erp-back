package knk.erp.api.shlee.schedule.specification;


import knk.erp.api.shlee.schedule.entity.RectifyAttendance;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RAS {//RectifyAttendanceSpecification

    public static Specification<RectifyAttendance> id(Long id){//deleteFalse
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
    
    public static Specification<RectifyAttendance> delFalse(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<RectifyAttendance> mid(String memberId){//memberId
        return (root, query, builder) -> builder.equal(root.get("memberId"), memberId);
    }
    public static Specification<RectifyAttendance> did(Long departmentId){//memberId
        return (root, query, builder) -> builder.equal(root.get("departmentId"), departmentId);
    }


}
