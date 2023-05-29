package com.roker.entity;

import lombok.Data;

import java.util.List;

/**
 * Email实体
 *
 * @author lpy96
 * @作者: Roker
 * @时间: 2023/5/29 0:29
 * @Copyright: Don`t be the same,be better!
 * @Description: $描述$
 * @date 2023/05/29
 */
@Data
public class DingEntity {

    String content;

    String[] AtMobiles;

    String messageUrl;
    String picUrl;
    String title;
    String text;


}
