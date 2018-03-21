package org.scnu.cluster.fansy.weka;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import org.scnu.util.Utils;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 *
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/20 下午9:19.
 */
public class SimpleKMeansClusterTest {
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
        ClusterInterface clusterInterface = new SimpleKMeansCluster();
        Map<String ,String> params = new HashMap<String,String>(){
            {
                put("-N","3");// 聚类个数
                put("-A","weka.core.EuclideanDistance");// 距离计算
                put("-I","100");// 循环次数
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
