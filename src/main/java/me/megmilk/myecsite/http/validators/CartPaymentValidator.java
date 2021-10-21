package me.megmilk.myecsite.http.validators;

import me.megmilk.myecsite.http.FlashBag;
import me.megmilk.myecsite.services.CartService;

import javax.servlet.http.HttpServletRequest;

/**
 * 配達先・お支払い方法 フォームのバリデーション
 */
public class CartPaymentValidator {
    /**
     * @return バリデーション結果。
     * <dl>
     *     <dt>true</dt><dd>すべて有効な入力だった</dd>
     *     <dt>false</dt><dd>一部でも無効な入力があった</dd>
     * </dl>
     */
    public static boolean validate(HttpServletRequest request) {
        //<editor-fold desc="配達先のラジオボタン">
        final String deliveryOption = request.getParameter("delivery_option");

        boolean found = false;

        for (String[] option : CartService.DELIVERY_OPTIONS) {
            if (null == deliveryOption) {
                break;
            }

            if (option[0].equals(deliveryOption)) {
                found = true;
                break;
            }
        }

        if (!found) {
            FlashBag.setMessagesBag(request, "delivery_option", "どれか選択してください。");
        }
        //</editor-fold>

        //<editor-fold desc="ご自宅以外の配達先住所">
        if (deliveryOption != null
            && deliveryOption.equals(CartService.DELIVERY_OPTIONAL)
        ) {
            final String optionalAddress = request.getParameter("optional_address");

            if (null == optionalAddress || 0 == optionalAddress.length()) {
                FlashBag.setMessagesBag(request, "optional_address", "入力してください。");
            }

            if (null != optionalAddress && optionalAddress.length() > 255) {
                FlashBag.setMessagesBag(request, "optional_address", "255文字以内で入力してください。");
            }
        }
        //</editor-fold>

        if (FlashBag.hasAnyMessage(request)) {
            FlashBag.setErrorTitle(request, "入力に不備があります。");
        }

        return !FlashBag.hasAnyMessage(request);
    }
}
