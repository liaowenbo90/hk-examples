package com.hk.weixin.example;

import com.hk.commons.util.ByteConstants;
import com.hk.core.authentication.security.AbstractUserDetailService;
import com.hk.core.authentication.security.SecurityUserPrincipal;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author: kevin
 * @date 2017年12月19日上午10:57:48
 */
@ServletComponentScan(basePackages = {"com.hk.core"})
@SpringBootApplication
public class WxMpDemoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WxMpDemoApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AbstractUserDetailService() {
            @Override
            protected SecurityUserPrincipal loadUserByLoginUsername(String username) {
                return new SecurityUserPrincipal(true, "1", "admin", "$2a$10$KgOArE6QpbY2iTQC0WGGS.hP72PQsHpToqbNVEEmUrd5LcEqrbzAG", "admin",
                        ByteConstants.ONE, "18800000000", "18800000000@xx.com", ByteConstants.ZERO, null, ByteConstants.ONE);
            }
        };
    }

}
