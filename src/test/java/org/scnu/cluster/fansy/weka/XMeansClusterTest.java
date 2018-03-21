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
public class XMeansClusterTest {
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
        ClusterInterface clusterInterface = new XMeansCluster();
        Map<String ,String> params = new HashMap<String,String>(){
            {
                put("-I","10");// 循环次数
                put("-M","1000");// 在kmeans中最大循环次数Improve-Parameter
                put("-J","1000");// 在kmeans中最大循环次数splitted centroids in the Improve-Structure
                put("-L","2");// 最小聚类个数
                put("-H","4");// 最大聚类个数
                put("-B","1.0");// 距离参数
                put("-C","0.0");// 阈值参数
                put("-D","weka.core.EuclideanDistance");// 距离
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
