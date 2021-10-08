package me.megmilk.myecsite.services;

import me.megmilk.myecsite.models.User;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class UserService {
    /**
     * プライマリキーによるユーザー検索
     */
    public static User find(int id) throws SQLException {
        final User user = User.find(id);

        // 存在しないプライマリキー
        if (null == user) {
            return null;
        }

        // 退会済みの利用者
        if (null != user.getDeleted_at()) {
            return null;
        }

        // アカウントが一時停止されている利用者
        if (user.getSuspended()) {
            return null;
        }

        return user;
    }

    /**
     * ログイン試行
     */
    public static User authenticate(HttpServletRequest request) throws SQLException {
        final User user = User.find(request.getParameter("email"));

        if (null == user) {
            return null;
        }

        System.out.println(
            DigestUtils.sha256Hex(
                request.getParameter("password")
            )
        );

        // TODO 本来ならパスワードは Spring の Bcrypt を使いたいけど、今回はほぼ素のJavaの学習。
        //  上記の理由により、暫定的に SHA256 を使います。
        final String password = DigestUtils.sha256Hex(
            request.getParameter("password")
        );

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
}
