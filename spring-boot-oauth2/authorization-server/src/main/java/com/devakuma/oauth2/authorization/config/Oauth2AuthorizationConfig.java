package com.devakuma.oauth2.authorization.config;

import com.devakuma.oauth2.authorization.security.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class Oauth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;
    private final CustomUserDetailService userDetailService;

//    @Value("${security.oauth2.jwt.signkey}")
//    private String signKey;

//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("testClientId")
//                .secret("{noop}testSecret")
//                .redirectUris("http://localhost:8081/oauth2/callback")
//                .authorizedGrantTypes("authorization_code")
//                .scopes("read", "write")
//                .accessTokenValiditySeconds(30000); // ????????? accessToken??? ????????????(???)
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

//    /**
//     * ?????? ????????? DB??? ?????? ????????????.
//     * @return
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(new JdbcTokenStore(dataSource));
//    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //allow check token
                .allowFormAuthenticationForClients();
    }


//    /**
//     * ?????? ?????? ????????? JWT ?????? ???????????? ????????????. ????????? ?????? ?????? ???????????? DB Table??? ????????? ??????.
//     *
//     * @param endpoints
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);
//        endpoints.accessTokenConverter(jwtAccessTokenConverter());
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailService);
    }

//    /**
//     * jwt converter??? ??????
//     *
//     * @return
//     */
//    @Bean
//    public JwtAccessTokenConverter jwtAccessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(signKey);
//        return converter;
//    }

    /**
     * jwt converter - ????????? ??? sign
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new FileSystemResource("authorization-server/src/main/resources/oauth2jwt.jks"), "oauth2jwtpass".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("oauth2jwt"));
        return converter;
    }
}