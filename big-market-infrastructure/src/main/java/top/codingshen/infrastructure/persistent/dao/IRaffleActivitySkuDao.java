package top.codingshen.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import top.codingshen.infrastructure.persistent.po.RaffleActivitySkuPO;

/**
 * @ClassName IRaffleActivitySkuDao
 * @Description 商品 sku dao
 * @Author alex_shen
 * @Date 2024/3/31 - 16:45
 */
@Mapper
public interface IRaffleActivitySkuDao {

    RaffleActivitySkuPO queryActivitySku(Long sku);

    void updateActivitySkuStock(Long sku);

    void clearActivitySkuStock(Long sku);
}
