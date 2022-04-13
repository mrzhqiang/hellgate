package hellgate.hub.script;

import hellgate.common.script.Script;

import javax.annotation.Nullable;
import java.util.List;

public interface ScriptService {

    /**
     * 列出推荐的剧本列表。
     * <p>
     * 按照推荐指数倒序排列。
     *
     * @return 剧本实体列表。
     */
    List<Script> listByRecommend();

    /**
     * 获取最新的剧本。
     * <p>
     * 以创建时间为基准，倒序获取时间最新的数据。
     *
     * @return 剧本实体。如果没有剧本，那么返回 Null 值。
     */
    @Nullable
    Script getNewest();
}
