package org.scnu.model;

import java.util.HashMap;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/8 下午1:58.
 */
public class Param extends HashMap<String,Object> {
    public Object getOneValue(String key){
        return get(key);
    }
}
