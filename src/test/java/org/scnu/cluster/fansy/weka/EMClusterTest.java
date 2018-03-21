package org.scnu.cluster.fansy.weka;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scnu.cluster.ClusterInterface;
import org.scnu.cluster.fansy.FrontBackCluster;
import org.scnu.model.Instance;
import org.scnu.util.Utils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/18 下午2:19.
 */
public class EMClusterTest {
    private  List<Instance> inputs;
    @Before
    public  void init(){
        try {
            this.inputs = Utils.getInputs();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test(){
        ClusterInterface clusterInterface = new EMCluster();
        Map<String ,String> params = new HashMap<String,String>(){
            {
                put("-I","100");// 循环次数
                put("-N","3");// 聚类个数
                put("-M","1e-6"); // 阈值
                put("-S","100");  // 随机数
            }
        };

        try {
            List<Instance> results = clusterInterface.runCluster(inputs, params);
            Utils.justForTest(inputs,results,3);
        }catch (Exception e){
            Assert.assertFalse(e.getMessage(),true);
        }


    }
}
