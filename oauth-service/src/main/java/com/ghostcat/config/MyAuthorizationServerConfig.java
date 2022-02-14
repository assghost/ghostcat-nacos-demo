package com.ghostcat.config;

import com.ghostcat.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    //@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 客户端配置（给谁发令牌）
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //从数据库取数据
        //clients.withClientDetails(clientDetailsService());

        // 从内存中取数据
        clients.inMemory()
                .withClient("my_client")
                .secret(passwordEncoder.encode("12345"))
                //有效时间 2小时
                .accessTokenValiditySeconds(72000)
                //密码授权模式和刷新令牌
                .authorizedGrantTypes("authorization_code", "refresh_token")
//                .resourceIds("product_api")
                //该client允许的授权类型 authorization_code,password,refresh_token,implicit,client_credentials
//                .authorizedGrantTypes(
//                        "authorization_code",
//                        "password",
//                        "client_credentials",
//                        "implicit",
//                        "refresh_token"
//                )
                .scopes("all")// 允许的授权范围
                .autoApprove(false)
                //回调地址
                .redirectUris("http://example.com")
                .scopes( "all");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new InMemoryTokenStore())
                //用password模式需要设置authenticationManager
                //.authenticationManager(authenticationManager)
                .userDetailsService(myUserDetailsService);
    }

    //检测token的策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                //允许form表单客户端认证,允许客户端使用client_id和client_secret获取token
                .allowFormAuthenticationForClients()
                // oauth/check_token 通过验证返回token信息
                .checkTokenAccess("isAuthenticated()")
                // oauth/token_key 获取token请求不进行拦截
                .tokenKeyAccess("permitAll()");
    }

}