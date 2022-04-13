package hellgate.api.controller.stage;

import hellgate.common.stage.Stage;

import java.util.List;

public interface StageService {

    /**
     * 列出推荐的舞台列表。
     * <p>
     * 按照推荐指数倒序排列。
     *
     * @return 舞台实体列表。
     */
    List<Stage> listByRecommend();

    /**
     * 获取最新的舞台。
     * <p>
     * 以创建时间为基准，倒序后获取最上面的数据。
     *
     * @return 舞台实体。
     */
    Stage getNewest();
}
