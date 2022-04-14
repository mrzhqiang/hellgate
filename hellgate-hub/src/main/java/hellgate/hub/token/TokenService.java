package hellgate.hub.token;

public interface TokenService {

    /**
     * 创建 Token 字符串。
     *
     * @param scope    范围，实际上表示为游戏名称。
     * @param username 用户名称。
     * @return Token 字符串。
     */
    String create(String scope, String username);
}
