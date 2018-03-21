package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * 参数：
 *
 -N <num>
    number of clusters. (default = 2).
 -S <num>
    Random number seed.
    (default 1)
 *
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/19 下午9:38.
 */
public class FarthestFirstCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {

        String[] options = WekaUtils.transformMap2StringArray(params);
        FarthestFirst cluster = new FarthestFirst();   // new instance of cluster
        cluster.setOptions(options);     // set the options
        return WekaUtils.classify(inputs,cluster);
    }
}
