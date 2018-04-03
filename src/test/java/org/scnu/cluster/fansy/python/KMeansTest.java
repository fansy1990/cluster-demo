package org.scnu.cluster.fansy.python;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.scnu.cluster.fansy.ClusterPythonInterface;
import org.scnu.model.RemoteHost;
import static org.scnu.util.Utils.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午10:05.
 */
public class KMeansTest {
    RemoteHost remoteHost;
    @Before
    public void init(){
        remoteHost = getExampleHost();
    }

    @Test
    public void test(){
        ClusterPythonInterface pythonAlgorithm = new KMeans();
        final String output = "kdata_out.csv";
        Map<String,String> params = new HashMap<String,String>(){
            {
                put(PYTHON_INPUT,
                        this.getClass().getClassLoader().getResource("kdata.csv").getPath());
                put(PYTHON_OUTPUT,output);
                put("splitter",",");
                put("k","3");
            }
        };
        pythonAlgorithm.runCluster(remoteHost,params);
        Assert.assertTrue(new java.io.File(output).exists());
    }
}
