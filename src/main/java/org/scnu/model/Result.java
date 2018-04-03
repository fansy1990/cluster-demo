package org.scnu.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午4:16.
 */
public class Result extends HashMap<String,String> {
    private static final String CODE = "CODE";
    private static final String INFORMATION = "INFORMATION";
    private HashMap<String,String> result = new HashMap<>();

    public void setExitCode(int code){
        result.put(CODE,String.valueOf(code));
    }

    public void setInformation(String information){
        result.put(INFORMATION,information);
    }

    public int getExitCode(){
        if(result.containsKey(CODE)){
            return Integer.parseInt(result.get(CODE));
        }else{
            return 0;
        }
    }
    public String getInformation(){
        if(result.containsKey(INFORMATION)){
            return result.get(INFORMATION);
        }else{
            return "";
        }
    }
}
