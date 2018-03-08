package org.scnu.cluster.fansy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import org.scnu.util.Utils;

import java.util.*;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/8 下午2:19.
 */
public class FrontBackClusterTest {
    private  List<Instance> inputs;
    @Before
    public  void init(){
        this.inputs = Utils.getInputs();
    }

    @Test
    public void test(){
        ClusterInterface clusterInterface = new FrontBackCluster();
        Map<String ,String> params = new HashMap<String,String>(){
            {
                put("percents","0.3");
            }
        };

        try {
            List<Instance> results = clusterInterface.runCluster(inputs, params);
            Utils.justForTest(inputs,results,2);
        }catch (Exception e){
            Assert.assertFalse(true);
        }


    }
}
