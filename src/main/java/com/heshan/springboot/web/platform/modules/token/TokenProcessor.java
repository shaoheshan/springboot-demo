package com.heshan.springboot.web.platform.modules.token;

import com.heshan.springboot.web.platform.common.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author liusongqing
 * @ClassName: TokenProcessor
 * @Description: token 产生器 防止重复提交
 * @date 2017年5月19日 下午1:15:54
 */
@Slf4j
@Component
public class TokenProcessor {
    /**
     * 保存token值的默认命名空间 默认为：easyjweb:platform-resubmit:token::
     */
    public static final String TOKEN_NAMESPACE = "disco:resubmit:token:";

    /**
     * 持有token名称的字段名 默认为：token_name
     */
    public static final String TOKEN_NAME_FIELD = "token_name";
    private static final Random RANDOM = new Random();
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 使用随机字串作为token名字保存token
     *
     * @return token
     */
    public Map<String, String> createToken() {
        String randomTokenName = generateGUID();
        return createToken(randomTokenName);
    }

    /**
     * 使用给定的字串作为token名字保存token
     *
     * @param randomTokenName
     * @return token 返回token值
     */
    private Map<String, String> createToken(String randomTokenName) {
        String token = generateGUID();
        Map<String, String> map = setCacheToken(randomTokenName, token);
        return map;
    }

    /**
     * 保存一个给定名字和值的token
     *
     * @param randomTokenName
     * @param token
     */
    private Map<String, String> setCacheToken(String randomTokenName, String token) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String tokenName = buildTokenCacheAttributeName(randomTokenName);
            // TODO 高并发下需考虑过期时间的的失效值
            redisUtils.set(tokenName, token, 60 * 60 * 2);
            map.put(TOKEN_NAME_FIELD, randomTokenName);
            map.put(randomTokenName, token);
        } catch (RuntimeException e) {
            String msg = "创建token令牌失败：" + e.getMessage();
            log.error(msg, e);
        }
        return map;
    }

    /**
     * 构建一个基于token名字的带有命名空间为前缀的token名字
     *
     * @param tokenName
     * @return
     */
    public String buildTokenCacheAttributeName(String tokenName) {
        return TOKEN_NAMESPACE + tokenName;
    }

    /**
     * 从请求域中获取给定token名字的token值
     *
     * @param tokenName
     * @return 返回表单中提交上来的token令牌值
     */
    public String getTokenValue(HttpServletRequest request, String tokenName) {
        if (!StringUtils.hasText(tokenName)) {
            return null;
        }
        Map<?, ?> params = request.getParameterMap();
        String[] tokens = (String[]) (String[]) params.get(tokenName);
        String token;
        if ((tokens == null) || (tokens.length < 1)) {
            log.error("根据token名称【" + tokenName + "】无法获取表单中对应的token值");
            return null;
        }
        token = tokens[0];
        return token;
    }

    /**
     * 从请求参数中获取token名字
     *
     * @return
     */
    public String getTokenName(HttpServletRequest request) {
        Map<?, ?> params = request.getParameterMap();

        if (!params.containsKey(TOKEN_NAME_FIELD)) {
            log.error("表单中无有效的token字段名称");
            return null;
        }

        String[] tokenNames = (String[]) params.get(TOKEN_NAME_FIELD);
        String tokenName;

        if ((tokenNames == null) || (tokenNames.length < 1)) {
            log.error("表单中token字段名称所对应的值无效");
            return null;
        }

        tokenName = tokenNames[0];
        return tokenName;
    }

    /**
     * 验证当前请求参数中的token是否合法，如果合法的token出现就会删除它，它不会再次成功合法的token
     *
     * @return 验证结果
     */
    public boolean validToken(HttpServletRequest request) {
        String tokenName = getTokenName(request);
        if (tokenName == null) {
            log.error("token名不能为空");
            return false;
        }

        String token = getTokenValue(request, tokenName);

        if (!StringUtils.hasText(token)) {
            log.error("根据token名称【" + tokenName + "】无法获取表单中对应的token值");
            return false;
        }

        String tokenCacheName = buildTokenCacheAttributeName(tokenName);
        String cacheToken = redisUtils.get(tokenCacheName);

        if (!token.equals(cacheToken)) {
            log.error("表单中获取到的token " + token + " 与服务端缓存的token " + cacheToken + "不一致.");
            return false;
        }
        redisUtils.delete(tokenCacheName);
        return true;
    }

    public String generateGUID() {
        return new BigInteger(165, RANDOM).toString(36).toUpperCase();
    }

    public String buildTokenHtml(Map<String, String> map) {
        String tokenHtml = "<input type=\"hidden\" id=\"{TOKEN_NAME_FIELD}\" value=\"{TOKEN_NAME_FIELD_VALUE_1}\"><br /><input type=\"hidden\" id=\"{TOKEN_NAME_FIELD_VALUE_2}\" value=\"{TOKEN_VALUE}\">";
        tokenHtml = tokenHtml.replace("{TOKEN_NAME_FIELD}", TokenProcessor.TOKEN_NAME_FIELD);
        tokenHtml = tokenHtml.replace("{TOKEN_NAME_FIELD_VALUE_1}", map.get(TokenProcessor.TOKEN_NAME_FIELD));
        tokenHtml = tokenHtml.replace("{TOKEN_NAME_FIELD_VALUE_2}", map.get(TokenProcessor.TOKEN_NAME_FIELD));
        tokenHtml = tokenHtml.replace("{TOKEN_VALUE}", map.get(map.get(TokenProcessor.TOKEN_NAME_FIELD)));
        return tokenHtml;
    }

}
