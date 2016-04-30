package logic;

import net.BusinessRequest;

/**
 * Created by gavin on 10/4/16.
 */
public class LoginRequest extends BusinessRequest {
    //http://kolvin.cn/Home/Login?UserName=admin&Password=1q2w3e4r&AutoLogin=false
    private String urlHead = "http://kolvin.cn/Home/Login";

    String username;
    String password;

    public LoginRequest(String username, String password) {
        setUsername(username);
        setPassword(password);
        String url = urlHead + "?UserName=" + username + "&Password=" + password + "&AutoLogin=false";
        setUrl(url);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
