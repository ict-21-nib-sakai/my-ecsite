package me.megmilk.myecsite.http;

import me.megmilk.myecsite.services.CartService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 各種定数をJSPに渡す
 * <p>
 * なお、クラス名とフィールド名の区切り文字は <strong>$</strong> 記号としました。
 *
 * @see <a href="https://teratail.com/questions/150182">(Java)jspでEL式(JSTL)を使用してEnumや定数クラスを呼び出す方法</a>
 */
@WebListener
public class ConstantsListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        // CartService の定数
        context.setAttribute("CartService$DELIVERY_HOME", CartService.DELIVERY_HOME);
        context.setAttribute("CartService$DELIVERY_OPTIONAL", CartService.DELIVERY_OPTIONAL);
        context.setAttribute("CartService$DELIVERY_OPTIONS", CartService.DELIVERY_OPTIONS);
    }
}
