package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.Cobweb;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  参数：
 -A <acuity>
     Acuity.
     (default=1.0)
 -C <cutoff>
     Cutoff.
     (default=0.002)
 -S <num>
     Random number seed.
     (default 42)

 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/20 下午9:38.
 */
public class CobwebCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {

        Cobweb cluster = new Cobweb();   // new instance of cluster
        cluster.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,cluster);
    }
}
