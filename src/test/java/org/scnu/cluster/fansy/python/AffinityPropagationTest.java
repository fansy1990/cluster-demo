package org.scnu.cluster.fansy.python;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scnu.cluster.fansy.ClusterPythonInterface;
import org.scnu.model.RemoteHost;

import java.util.HashMap;
import java.util.Map;

import static org.scnu.util.Utils.*;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午10:05.
 */
public class AffinityPropagationTest {
    RemoteHost remoteHost;
    @Before
    public void init(){
        remoteHost = getExampleHost();
    }

    @Test
    public void test(){
        ClusterPythonInterface pythonAlgorithm = new AffinityPropagation();
        final String output = "affinity_propagation_out.csv";
        Map<String,String> params = new HashMap<String,String>(){
            {
                put(PYTHON_INPUT,
                        this.getClass().getClassLoader().getResource("kdata.csv").getPath());
                put(PYTHON_OUTPUT,output);
                put("splitter",",");
                put("damping","0.5");
                put("max_iter","200");
                put("convergence_iter","15");
                put("preference","-50");
            }
        };
        pythonAlgorithm.runCluster(remoteHost,params);
        Assert.assertTrue(new java.io.File(output).exists());
    }
}
