package top.codingshen.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.AwardPO;

import java.util.List;

/**
 * @ClassName IAwardDao
 * @Description 奖品表 Dao
 * @Author alex_shen
 * @Date 2024/3/2 - 22:22
 */
@Mapper
public interface IAwardDao {
    List<AwardPO> queryAwardList();
}
