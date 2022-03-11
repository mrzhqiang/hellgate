package hellgate.common;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * 消息工具类。
 */
public class Messages {
    private Messages() {
        // no instances
    }

    /* 警告消息常量 */
    public static final String ALERT_TYPE = "alert-type";
    public static final String ALERT_SUCCESS = "alert-success";
    public static final String ALERT_INFO = "alert-info";
    public static final String ALERT_PRIMARY = "alert-primary";
    public static final String ALERT_SECOND = "alert-second";
    public static final String ALERT_DANGER = "alert-danger";
    public static final String ALERT_WARNING = "alert-warning";

    public static final String ALERT_KEY = "alert";

    /* 具体消息：对应 i18n 中的 key 值，用于代码中传输的消息 */
    public static final String LOGIN_FAILED = "message.login.account.not-found";
    public static final String REGISTER_FAILED = "message.register.failed";
    public static final String REGISTER_SUCCESSFUL = "message.register.successful";

    public static <M extends Model> void success(M model, String messageCode) {
        alertMessage(model, messageCode, ALERT_SUCCESS);
    }

    public static Map<String, Object> success(Map<String, Object> model, String messageCode) {
        model.put(ALERT_TYPE, ALERT_SUCCESS);
        model.put(ALERT_KEY, messageCode);
        return model;
    }

    public static <M extends Model> void info(M model, String messageCode) {
        alertMessage(model, messageCode, ALERT_INFO);
    }

    public static Map<String, Object> info(Map<String, Object> model, String messageCode) {
        model.put(ALERT_TYPE, ALERT_INFO);
        model.put(ALERT_KEY, messageCode);
        return model;
    }

    public static <M extends Model> void primary(M model, String messageCode) {
        alertMessage(model, messageCode, ALERT_PRIMARY);
    }

    public static Map<String, Object> primary(Map<String, Object> model, String messageCode) {
        model.put(ALERT_TYPE, ALERT_PRIMARY);
        model.put(ALERT_KEY, messageCode);
        return model;
    }

    public static <M extends Model> void second(M model, String messageCode) {
        alertMessage(model, messageCode, ALERT_SECOND);
    }

    public static Map<String, Object> second(Map<String, Object> model, String messageCode) {
        model.put(ALERT_TYPE, ALERT_SECOND);
        model.put(ALERT_KEY, messageCode);
        return model;
    }

    public static <M extends Model> void danger(M model, String messageCode) {
        alertMessage(model, messageCode, ALERT_DANGER);
    }

    public static Map<String, Object> danger(Map<String, Object> model, String messageCode) {
        model.put(ALERT_TYPE, ALERT_DANGER);
        model.put(ALERT_KEY, messageCode);
        return model;
    }

    public static <M extends Model> void warning(M model, String messageCode) {
        alertMessage(model, messageCode, ALERT_WARNING);
    }

    public static Map<String, Object> warning(Map<String, Object> model, String messageCode) {
        model.put(ALERT_TYPE, ALERT_WARNING);
        model.put(ALERT_KEY, messageCode);
        return model;
    }

    private static <M extends Model> void alertMessage(M model, String messageCode, String alertSuccess) {
        if (model == null) {
            return;
        }

        if (model instanceof RedirectAttributes) {
            RedirectAttributes flashAttribute = ((RedirectAttributes) model);
            flashAttribute.addFlashAttribute(ALERT_TYPE, alertSuccess);
            flashAttribute.addFlashAttribute(ALERT_KEY, messageCode);
        }

        model.addAttribute(ALERT_TYPE, alertSuccess);
        model.addAttribute(ALERT_KEY, messageCode);
    }
}
