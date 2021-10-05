package knk.erp.api.shlee.domain.Materials.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialsDTO {
    private List<String> materials;
}
