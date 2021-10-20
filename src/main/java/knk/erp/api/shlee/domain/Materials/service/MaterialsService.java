package knk.erp.api.shlee.domain.Materials.service;

import knk.erp.api.shlee.domain.Materials.dto.Create_MaterialsDTO_RES;
import knk.erp.api.shlee.domain.Materials.dto.MaterialsDTO;
import knk.erp.api.shlee.domain.Materials.dto.Read_MaterialsDTO_RES;
import knk.erp.api.shlee.domain.Materials.entity.Materials;
import knk.erp.api.shlee.domain.Materials.repository.MaterialsRepository;
import knk.erp.api.shlee.domain.account.entity.Authority;
import knk.erp.api.shlee.domain.account.entity.Member;
import knk.erp.api.shlee.domain.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialsService {
    private final MemberRepository memberRepository;
    private final MaterialsRepository materialsRepository;

    //장기자재현황 사진 파일 이름 리스트 저장
    public Create_MaterialsDTO_RES createMaterials(MaterialsDTO materialsDTO) {
        try{
            Materials before = materialsRepository.findAllByDeletedFalse();

            if(before != null) {
                before.setDeleted(true);
                materialsRepository.save(before);
            }

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Member writer = memberRepository.findByMemberIdAndDeletedIsFalse(authentication.getName());
            if(!writer.getAuthority().equals(Authority.ROLE_MATERIALS)){
                return new Create_MaterialsDTO_RES("CMTR003", "업로드 권한 없음");
            }

            materialsRepository.save(Materials.builder().materials(materialsDTO.getMaterials()).build());

            return new Create_MaterialsDTO_RES("CMTR001");
        }catch(Exception e){
            return new Create_MaterialsDTO_RES("CMTR002", e.getMessage());
        }
    }

    //장기자재현황 사진 파일 이름 리스트 Get
    public Read_MaterialsDTO_RES readMaterials(){
        try{
            Materials materials = materialsRepository.findAllByDeletedFalse();

            return new Read_MaterialsDTO_RES("RMTR001", materials.getMaterials());
        }catch(Exception e){
            return new Read_MaterialsDTO_RES("RMTR002", e.getMessage());
        }
    }
}