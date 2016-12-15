package com.chazuo.czlib.db;

/**
 * Created by LiQiong on 2016/11/26 16:29
 *  * 注意：
 * 在设计数据库的时候，我们要先把主键定一下。
 * 切勿在有数据的情况下，后添加主键
 * 这种操作是不允许的。
 *
 * 后添加的主键设置不起作用。
 */

public @interface AutoPrimaryKey {

}
