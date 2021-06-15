package knk.erp.api.shlee.schedule.specification;

import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleSpecification {
    public static Specification<Schedule> withMemberId(String memberId, String viewOption){
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Predicate memberIdPredicate = builder.equal(root.get("memberId"), memberId);
            predicates.add(memberIdPredicate);

            Predicate datePredicate = builder.equal(root.get("viewOption"), viewOption);
            predicates.add(datePredicate);



            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
