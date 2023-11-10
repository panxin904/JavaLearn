package community.community.provider;

import com.alibaba.fastjson.JSON;
import community.community.dto.AccessTokenDTO;
import community.community.dto.GithubUser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Coder on 2023/9/17 & 19:25.
 **/
@Component // 将类初始化到Spring容器的上下文，用Spring容器管理对象的创建
@Slf4j
public class GithubProvider {
    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    //通过认证后获取AcrossToken
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);

        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(500000L, TimeUnit.MILLISECONDS).readTimeout(500000L, TimeUnit.MILLISECONDS).build();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .post(body)
                .url("https://github.com/login/oauth/access_token")
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string1 = response.body().string();
            String token = string1.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            log.error("Token Error: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public GithubUser getUser(String  access_token){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                    .url("https://api.github.com/user")
                    .header("Authorization", "token " + access_token)
                    .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return JSON.parseObject(string, GithubUser.class);
        } catch (IOException e){
        }
        return null;
    }
}
