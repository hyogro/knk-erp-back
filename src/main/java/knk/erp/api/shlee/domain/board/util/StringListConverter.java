package knk.erp.api.shlee.domain.board.util;


import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class StringListConverter implements AttributeConverter<List<String>, String> {
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        StringJoiner sj = new StringJoiner(",");
        try {
            for(String item : attribute){
                sj.add(item);
            }
        }catch (Exception e){
            return null;
        }
        return sj.toString();
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        List<String> list = new ArrayList<>();
        try {
            list = Arrays.asList(dbData.split(","));
        }catch (Exception e){
            list = null;
        }
        return list;
    }
}
