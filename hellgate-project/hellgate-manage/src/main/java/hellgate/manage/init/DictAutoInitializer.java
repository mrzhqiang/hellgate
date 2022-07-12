package hellgate.manage.init;

import com.google.common.base.Stopwatch;
import hellgate.common.dict.DictGroup;
import hellgate.common.dict.DictGroupData;
import hellgate.common.dict.DictGroupMapper;
import hellgate.common.dict.DictGroupRepository;
import hellgate.common.dict.DictItem;
import hellgate.common.dict.DictItemMapper;
import hellgate.common.dict.DictItemRepository;
import hellgate.common.util.Jsons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DictAutoInitializer extends BaseAutoInitializer {

    private final DictGroupRepository groupRepository;
    private final DictGroupMapper groupMapper;
    private final DictItemRepository itemRepository;
    private final DictItemMapper itemMapper;

    @Value(ResourceUtils.CLASSPATH_URL_PREFIX + "data/dict.json")
    private Resource resource;

    public DictAutoInitializer(DictGroupRepository groupRepository,
                               DictGroupMapper groupMapper,
                               DictItemRepository itemRepository,
                               DictItemMapper itemMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean onInit() throws Exception {
        log.info("开始自动初始化数据字典...");
        Stopwatch stopwatch = Stopwatch.createStarted();

        if (resource == null) {
            log.warn("数据字典 json 文件不存在，跳过初始化过程，耗时：{}", stopwatch.stop());
            return false;
        }

        File dataFile = resource.getFile();
        List<DictGroupData> groups = Jsons.listFromFile(dataFile, DictGroupData.class);
        if (CollectionUtils.isEmpty(groups)) {
            log.warn("数据字典 json 文件没有解析到数据，跳过初始化过程，耗时：{}", stopwatch.stop());
            return false;
        }

        for (DictGroupData group : groups) {
            log.debug("保存数据字典数据 {}", group);

            DictGroup groupEntity = groupMapper.toEntity(group);
            groupEntity.setType(DictGroup.Type.DB);
            log.debug("保存数据字典分组 {}", groupEntity);
            DictGroup dictGroup = groupRepository.save(groupEntity);

            if (CollectionUtils.isEmpty(group.getItems())) {
                log.warn("数据字典分组 {} 的字典项为空", groupEntity.getName());
                continue;
            }

            List<DictItem> itemList = group.getItems().stream()
                    .map(itemMapper::toEntity)
                    .peek(it -> it.setGroup(dictGroup))
                    .collect(Collectors.toList());
            log.debug("保存字典项 {} 到字典分组 {}", itemList, groupEntity.getName());
            itemRepository.saveAll(itemList);
        }

        log.info("数据字典自动初始化完成，耗时：{}", stopwatch.stop());
        return true;
    }
}
