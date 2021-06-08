package knk.erp.api.shlee.account.service;

import knk.erp.api.shlee.account.entity.DepartmentRepository;
import knk.erp.api.shlee.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final DepartmentRepository departmentRepository;
    private final BCryptPasswordEncoder passwordEncoder;


}
