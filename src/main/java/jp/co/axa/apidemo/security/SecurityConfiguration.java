// package jp.co.axa.apidemo.security;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.factory.PasswordEncoderFactories;
// import org.springframework.security.crypto.password.PasswordEncoder;
//
/// **
// * application security configuration
// */
// @Configuration
// @EnableWebSecurity
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    /**
//     * the admin role constant
//     */
//    private static final String ADMIN_ROLE = "ADMIN";
//
//    /**
//     * admin username is read from application properties file
//     */
//    @Value("${spring.security.admin.user}")
//    private String adminUsername;
//
//    /**
//     * admin password is read from application properties file
//     */
//    @Value("${spring.security.admin.password}")
//    private String adminPassword;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/api/**", "/h2-console/**")
//                .authenticated()
//                .anyRequest()
//                .permitAll()
//                .and()
//                .httpBasic();
//        http.csrf().disable();
//        // to check password in each request
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser(adminUsername)
//                .password(passwordEncoder().encode(adminPassword))
//                .roles(ADMIN_ROLE);
//    }
//
//    /**
//     * password encoder to encode password for the user details service
//     *
//     * @return password encoder
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
// }
