package knk.erp.api.shlee.admin.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public String makeAdmin(){
        String memberId = "admin";
        String password = passwordEncoder.encode("admin1234");
        String memberName = "관리자";
        //Long departmentId = 1L;
        String phone = "000-0000-0000";
        LocalDate joiningDate = LocalDate.now();

        try {

            Member member = Member.builder().memberId(memberId).password(password).memberName(memberName).phone(phone).joiningDate(joiningDate).build();
            memberRepository.save(member);
            return "success";
        }catch (Exception e){
            return "failed: " + e.getMessage();
        }
    }

    public String makeDefaultUser(){
        String division = "#";
        String divisionLine = "!";
        String data =
                "박선자#2006.05.11#010-3721-9563#dhsmf3721@naver.com#전라북도 전주시 완산구 평화동2가 동도미소드림아파트 102/605!" +
                        "김정환#2009.03.02#010-3919-9563#catv9292@hanmail.net#전라북도 전주시 완산구 평화동2가 동도미소드림아파트 102/605!" +
                        "정일업#2011.09.15#010-3657-4681#illeop@nate.com#전라북도 전주시 완산구 오공로 71, 110동 202호 (중동, 호반베르디움아파트)!" +
                        "박성욱#2010.07.01#010-2212-1883#sungwok77@hanmail.net#전라북도 전주시 완산구 평화동2가 952번지, 영무예다음아파트 103동 1003호!" +
                        "김명지#2021.04.05#010-9934-2091#-#전라북도 전주시 완산구 맏내1길 5, 101동 508호(평화동2가, 우성신성타운)!" +
                        "김효준#2021.04.05#010-5517-9563#gywns9563@naver.com#전라북도 전주시 완산구 평화동2가 동도미소드림아파트 102/605!" +
                        "이상훈#2021.6.1#010-6828-3435#-#전라북도 전주시 완산구 구이로 2094, 103동 803호(평화동2가,평화주공그린타운)!" +
                        "임현주#2021.6.1#010-2465-3895#-#전라북도 전주시 완산구 구이로 2094, 103동 803호(평화동2가,평화주공그린타운)!" +
                        "유태수#2018.4.2#010-3675-3539#-#전라북도 전주시 덕진구 호반로 11, 201동 601호(송천동1가,센트럴파크2단지아파트)!" +
                        "김정현#2011.09.15#010-9435-8744#skawk5025@daum.net#전라북도 전주시 덕진구 틀못4길 17, 205/702!" +
                        "황삼택#2018.10.29#010-3671-9133#-#전라북도 전주시 완산구 장승배기로 15, 402동 1402호(삼천동1가, 삼천주공4단지아파트)!" +
                        "조인국#2019.4.1#010-8302-5775#-#전라북도 전주시 완산구 거마산로 8-8(삼천동1가)!" +
                        "박재섭#2021.2.1#010-8956-9322#-#전라북도 완주군 소양면 대흥문화길 47!" +
                        "김재석#2018.8.1#010-7736-1195#-#전라북도 전주시 완산구 평화동2가 902-4, 민진빌 302호!" +
                        "이명수#2020.5.11#010-4141-9801#-#전라북도 임실군 강진면 호국로 56-6!" +
                        "양승섭#2019.6.27#010-9222-2005#-#전라북도 전주시 완산구 평화로 181, 5동 1605호(평화동1가,코오롱아파트)!" +
                        "정성옥#2016.10.17#010-4840-5767#-#전라북도 완주군 구이면 구이로 1495-6!" +
                        "고정훈#2019.04.01#010-2442-1530#-#전라북도 전주시 덕진구 인후동1가 850-8 스위트하우스 204호!" +
                        "강희성#2017.04.03#010-4660-3434#-#전라북도 전주시 덕진구 세병로 112, 데시앙7BL 701동 1701호!" +
                        "이기범#2021.2.1#010-4846-8130#-#전라북도 김제시 도작로 33, 306동 805호(신풍동,부영아파트)!" +
                        "송한나#2021.5.17#010-5319-4269#-#전라북도 완주군 상관면 신리로 99, 107동 1501호(신세대지큐빌아파트)!" +
                        "하태길#2019.7.1#010-2317-5289#-#전라북도 전주시 완산구 흑석로 53, 대승푸른맨션 102동 1201호(서서학동,푸른맨션)!" +
                        "정영우#2019.8.19#010-5880-1659#-#전라북도 전주시 완산구 평화로 95, 104동 306호(평화동2가,호반리젠시빌)!" +
                        "유상현#2020.12.01#010-6695-0128#-#전라북도 전주시 완산구 효자동1가 용머리로 20 효자 현대아파트 106동 208호!" +
                        "김환철#2015.11.23#010-4005-4673#-#전라북도 전주시 덕진구 솔내7길 17-17(송천동1가)!" +
                        "홍수아#2018.04.02#010-3866-4242#-#전라북도 전주시 완산구 양지3길 25, 102동 303호(평화동2가, 엠코타운아파트)!" +
                        "김수원#2018.6.25#010-8700-4170#-#전라북도 전주시 완산구 거마평로 189-13(효자동1가)!" +
                        "김준성#2020.5.25#010-7176-5862#-#전라북도 전주시 완산구 평화로 120, 103동 503호 (평화동2가,두산경복궁아파트)!" +
                        "이상우#2018.04.03#010-9298-8115#dkvk00@naver.com#전라북도 전주시 완산구 평화15길 18-8, 엘리트빌라 201호!" +
                        "전예린#2020.12.10#010-3248-9279#-#전라북도 전주시 완산구 척동7길 6-4(효자동3가)!" +
                        "허성회#2019.8.19#010-7616-0102#-#전라북도 전주시 완산구 새내로 239, 108동 402호(효자동2가,더샾 효자아파트), 전라북도 전주시 완산구 효자동2가 1168-2번지 동원빌 201호!" +
                        "서인원#2019.8.19#010-6399-1588#-#전라북도 전주시 완산구 밤나무2길 4-10(효자동1가)!" +
                        "한상권#2021.5.12#010-5591-2090#-#전라북도 전주시 완산구 강변로 120, 101동402호(효자동1가,한강아파트)!" +
                        "이지율#2018.03.05#010-9354-2817#-#전라북도 전주시 완산구 서곡로 8, 108동 803호(효자동,서곡주공)!" +
                        "김윤정#2019.07.01#010-6297-7270#-#전라북도 전주시 완산구 평화로 120, 101동 2001호(평화동2가,두산경복궁아파트)!" +
                        "진희정#2017.06.01#010-3033-1207#-#전라북도 전주시 완산구 효자동2가 1162-15, 프라임빌 402호!" +
                        "박정민#2021.01.01#010-8647-2270#-#전라북도 군산시 미룡로 42, 312동 1302호(미룡동,미룡(3)주공아파트)!";
        

        String[] memberArray = data.split(divisionLine);

        int index = 1;
        for(String member : memberArray){
            String[] memberData = member.split(division);
            String memberId = "knk" + ((index < 10) ? "00": "0");
            String password = passwordEncoder.encode("00000000");
            String memberName = memberData[0];
            String[] dateData = memberData[1].split(".");

            LocalDate joiningDate = LocalDate.of(Integer.parseInt(dateData[0]), Integer.parseInt(dateData[1]), Integer.parseInt(dateData[2]));

            String phone = memberData[2];
            String email = memberData[3];
            String address = memberData[4];

            try {
                Member memberEntity = Member.builder().memberId(memberId).password(password).memberName(memberName).phone(phone).email(email).address(address).joiningDate(joiningDate).build();
                memberRepository.save(memberEntity);
            }catch (Exception e){
                return "failed: " + e.getMessage();
            }
            index++;
        }

        return "success";
    }
}
