package com.heshan.springboot.web.platform.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 *
 * @author
 * @email
 * @date 2012-07-20 13:22
 */
@SuppressWarnings("serial")
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
