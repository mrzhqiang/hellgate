package hellgate.common.util;

import hellgate.common.exception.ExceptionLogData;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * 视图工具。
 */
public final class Views {
    private Views() {
        // no instances
    }

    /**
     * 异常视图的路径前缀。
     */
    public static final String EXCEPTION_VIEW_NAME_PREFIX = "error";

    /**
     * 错误的模型视图。
     * <p>
     * 异常日志数据包含 HTTP 状态码，可以作为错误的具体路径，也为模型视图提供了状态。
     *
     * @return 模型视图。
     */
    public static ModelAndView ofError(ExceptionLogData data) {
        ModelAndView view = new ModelAndView(EXCEPTION_VIEW_NAME_PREFIX);
        view.setStatus(HttpStatus.resolve(data.getStatus()));
        view.addObject("data", data);
        return view;
    }
}
