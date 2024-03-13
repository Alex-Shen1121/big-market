package top.codingshen.trigger.api;

import top.codingshen.trigger.api.dto.RaffleAwardListRequestDTO;
import top.codingshen.trigger.api.dto.RaffleAwardListResponseDTO;
import top.codingshen.trigger.api.dto.RaffleRequestDTO;
import top.codingshen.trigger.api.dto.RaffleResponseDTO;
import top.codingshen.types.model.Response;

import java.util.List;

/**
 * @ClassName IRaffleService
 * @Description 抽奖服务接口
 * @Author alex_shen
 * @Date 2024/3/10 - 17:57
 */
public interface IRaffleService {
    /**
     * 策略装配接口
     *
     * @param strategyId 策略 id
     * @return 装备结果
     */
    Response<Boolean> strategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表配置
     *
     * @param requestDTO 抽奖奖品列表查询请求参数
     * @return 奖品列表数据
     */
    Response<List<RaffleAwardListResponseDTO>> queryRaffleAwardList(RaffleAwardListRequestDTO requestDTO);

    /**
     * 随机抽奖接口
     *
     * @param requestDTO 请求参数
     * @return 抽奖结果
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);

    /**
     * 测试 单一前置抽奖接口
     *
     * @param requestDTO requestDTO – 请求参数
     * @return 抽奖结果
     */
    Response<RaffleResponseDTO> test_getRandomAwardId(RaffleRequestDTO requestDTO);
}
