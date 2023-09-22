package community.community.controller;

import community.community.dto.AccessTokenDTO;
import community.community.dto.GithubUser;
import community.community.mapper.UsersMapper;
import community.community.model.Users;
import community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by Coder on 2023/9/14$ & 21:26$.
 **/
@Controller //路由承载者
public class OAuthorizeController {
    @Autowired // 告诉Spring容器自动加载到当前实例中
    private GithubProvider githubProvider;
    @Value("${github.client.id}") //自动读取配置文件中对应属性的值
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @Autowired
    private UsersMapper usersMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null){
            //登陆成功，写入cookie和session
            Users users = new Users();
            users.setToken(UUID.randomUUID().toString());
            users.setName(githubUser.getName());
            users.setAccountId(String.valueOf(githubUser.getId()));
            users.setGmtCreate(System.currentTimeMillis());
            users.setGmtModified(users.getGmtCreate());
            usersMapper.insert(users);
            request.getSession().setAttribute("user", githubUser);
            return "redirect:/";
        }else {
            //登陆失败，重新登陆
            return "redirect:/";
        }
    }
}
