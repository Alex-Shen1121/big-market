package top.codingshen.types.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName Response
 * @Description 统一响应
 * @Author alex_shen
 * @Date 2024/3/10 - 17:58
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private String code;
    private String info;
    private T data;
}
