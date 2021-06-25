package knk.erp.api.shlee.config;

import knk.erp.api.shlee.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableJpaAuditing
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;

    // 정적 자원에 대해서는 Security 설정을 적용하지 않음.
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //2021-06-08 15:36 이상훈 추가 Cors 세팅
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())

                .and()
                .csrf().disable()
                // form 기반의 로그인에 대해 비활성화
                .formLogin().disable()

                // 로그인 API 권한없이 접근 가능하도록 설정
                .authorizeRequests()
                .antMatchers("/account/login", "/admin/*").permitAll()

                // 회원 정보 목록 읽어오기, 회원 정보 수정, 회원 삭제, 회원 생성, 회원 정보 상세보기는 관리자 이상만 가능하도록 설정
                .antMatchers("/account/readMember", "/account/{memberId}", "/account", "/account/signup")
                .hasAnyRole("LVL3", "LVL4", "ADMIN")

                .antMatchers("/department/readDepartmentNameAndMemberCount").authenticated()

                // 부서 관리는 관리자 이상만 가능하게 설정
                .antMatchers("/department", "/department/{dep_id}", "/department/updateLeader/{dep_id}",
                        "/department/addMember/{dep_id}", "/department/deleteMember/{dep_id}")
                .hasAnyRole("LVL3", "LVL4", "ADMIN")

                // 나머지 API 는 권한 인증 필요
                .anyRequest().authenticated()
                .and()

                // 토큰을 활용하면 세션이 필요 없으므로 STATELESS 로 설정하여 Session 을 사용하지 않는다.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .apply(new knk.erp.api.shlee.config.JwtSecurityConfig(tokenProvider));
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}