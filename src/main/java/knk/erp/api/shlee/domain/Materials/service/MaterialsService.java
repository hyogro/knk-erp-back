package knk.erp.api.shlee.domain.Materials.service;

import knk.erp.api.shlee.domain.Materials.dto.MaterialsDTO;
import knk.erp.api.shlee.domain.Materials.entity.Materials;
import knk.erp.api.shlee.domain.Materials.repository.MaterialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialsService {
    private final MaterialsRepository materialsRepository;

    /* 장기자재현황 사진 파일 이름 리스트 저장 */
    public void createMaterials(MaterialsDTO materialsDTO) {
        Materials before = materialsRepository.findAllByDeletedFalse();

        if(before != null) {
            before.setDeleted(true);
            materialsRepository.save(before);
        }

        materialsRepository.save(Materials.builder().materials(materialsDTO.getMaterials()).build());
    }

    /* 장기자재현황 사진 파일 이름 리스트 Get */
    public List<String> readMaterials() { return materialsRepository.findAllByDeletedFalse().getMaterials(); }

}
