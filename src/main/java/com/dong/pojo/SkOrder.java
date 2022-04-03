package com.dong.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SkOrder implements Serializable {
    @TableId(type = IdType.AUTO)
    private  Long id;

    private Long userId;

    private Long orderId;

    private Long goodsId;


}
