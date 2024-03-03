package top.codingshen.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.Award;

import java.util.List;

/**
 * @ClassName IAwardDao
 * @Description 奖品表 Dao
 * @Author alex_shen
 * @Date 2024/3/2 - 22:22
 */
@Mapper
public interface IAwardDao {
    List<Award> queryAwardList();
}
