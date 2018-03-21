package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.EM;
import weka.clusterers.sIB;

import java.util.List;
import java.util.Map;

/**
 * -I <num>
    maximum number of iterations
    (default 100).
 -M <num>
    minimum number of changes in a single iteration
    (default 0).
 -N <num>
    number of clusters.
    (default 2).
 -R <num>
    number of restarts.
    (default 5).
 -U
    set not to normalize the data
    (default true).
 -V
    set to output debug info
    (default false).
 -S <num>
    Random number seed.
    (default 1)

 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/19 下午9:38.
 */
public class SIBCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {
        sIB sIB = new sIB();
        sIB.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,sIB);
    }
}
