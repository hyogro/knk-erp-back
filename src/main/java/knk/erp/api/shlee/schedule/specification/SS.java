package knk.erp.api.shlee.schedule.specification;

import knk.erp.api.shlee.account.entity.Department;
import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.schedule.entity.Schedule;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SS {//ScheduleSpecification

    public static Specification<Schedule> delFalse(){//deleteFalse
        return (root, query, builder) -> builder.isFalse(root.get("deleted"));
    }

    public static Specification<Schedule> mid(String memberId){//memberId
        return (root, query, builder) -> builder.equal(root.get("memberId"), memberId);
    }

    public static Specification<Schedule> viewOption(String viewOption, String mid, Long did){//viewOption
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            String[] viewOp = viewOption.split(" ");
            for(String vo : viewOp){
                switch (vo) {
                    case "all":
                        predicates.add(VOP_ALL().toPredicate(root, query, builder));
                        break;
                    case "dep":
                        predicates.add(VOP_DEP(did).toPredicate(root, query, builder));
                        break;
                    case "own":
                        predicates.add(VOP_OWN(mid).toPredicate(root, query, builder));
                        break;
                }
            }

            return builder.or(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Schedule> VOP_ALL(){
        return ((root, query, builder) -> builder.equal(root.get("viewOption"), "all"));
    }


    public static Specification<Schedule> VOP_DEP(Long did) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("viewOption"), "dep"));

            Join<Schedule, Member> sm = root.join("author");

            predicates.add(builder.equal(sm.get("department").get("id"), did));

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Specification<Schedule> VOP_OWN(String mid){
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("viewOption"), "own"));

            Join<Schedule, Member> sm = root.join("author");

            predicates.add(builder.equal(sm.get("memberId"), mid));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
