package spidaApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order (1*1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.anyRequest().fullyAuthenticated()
				.and()
			.formLogin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authLdap) throws Exception {
		authLdap
					.ldapAuthentication()
						.userDnPatterns("uid={0},ou=people")
						.groupSearchBase("ou=groups")
						.contextSource()
							.url("ldap://localhost:8389/dc=springframework,dc=org")
							.and()
						.passwordCompare()
							.passwordEncoder(new LdapShaPasswordEncoder())
							.passwordAttribute("userPassword");
	}

}