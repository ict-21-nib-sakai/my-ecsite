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
        // 配達先のラジオボタン
        final String deliveryOption = request.getParameter("delivery_option");

        // 配達先の選択肢をバリデーション
        validateDeliveryOption(request);

        // ご自宅以外の配達先住所をバリデーション
        validateOptionalAddress(request);

        // お支払い方法"
        validatePaymentMethod(request);

        if (FlashBag.hasAnyMessage(request)) {
            FlashBag.setErrorTitle(request, "入力に不備があります。");
        }

        return !FlashBag.hasAnyMessage(request);
    }

    /**
     * 配達先の選択肢をバリデーション
     */
    private static void validateDeliveryOption(HttpServletRequest request) {
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
    }

    /**
     * ご自宅以外の配達先をバリデーション
     */
    private static void validateOptionalAddress(HttpServletRequest request) {
        // 配達先のラジオボタン
        final String deliveryOption = request.getParameter("delivery_option");

        // ラジオボタンが未選択の場合は、バリデーションできないので無視する
        if (null == deliveryOption) {
            return;
        }

        // ラジオボタンが「配達先を指定」じゃない場合は、対象外なので無視する
        if (!CartService.DELIVERY_OPTIONAL.equals(deliveryOption)) {
            return;
        }

        // ご自宅以外の配達先
        final String optionalAddress = request.getParameter("optional_address");

        if (null == optionalAddress || 0 == optionalAddress.length()) {
            FlashBag.setMessagesBag(request, "optional_address", "入力してください。");
        }

        if (null != optionalAddress && optionalAddress.length() > 255) {
            FlashBag.setMessagesBag(request, "optional_address", "255文字以内で入力してください。");
        }
    }

    /**
     * お支払い方法をバリデーション
     */
    private static void validatePaymentMethod(HttpServletRequest request) {
        final String paymentMethod = request.getParameter("payment_method");

        boolean found = false;

        for (String[] option : CartService.PAYMENT_METHODS) {
            if (null == paymentMethod) {
                break;
            }

            if (option[0].equals(paymentMethod)) {
                found = true;
                break;
            }
        }

        if (!found) {
            FlashBag.setMessagesBag(request, "payment_method", "どれか選択してください。");
        }
    }
}
