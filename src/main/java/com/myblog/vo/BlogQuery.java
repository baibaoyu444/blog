package com.myblog.vo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BlogQuery {
    /*  单独封装查询模块  */

    /*  查询的标题  */
    private String title;

    /*  查询的类型的id  */
    private Long typeId;

    /*  是否为推荐的  */
    private boolean recommend;

    public boolean isRecommend() {
        return recommend;
    }
}
