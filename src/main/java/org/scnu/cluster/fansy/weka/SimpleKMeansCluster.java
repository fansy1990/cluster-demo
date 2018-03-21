package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.Cobweb;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  参数：
 Options specific to weka.clusterers.SimpleKMeans:

 -N <num>
 number of clusters.
 (default 2).
 -V
 Display std. deviations for centroids.

 -M
 Don't replace missing values with mean/mode.

 -A <classname and options>
 Distance function to use.
 (default: weka.core.EuclideanDistance)
 -I <num>
 Maximum number of iterations.

 -O
 Preserve order of instances.

 -S <num>
 Random number seed.
 (default 10)


 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/20 下午9:38.
 */
public class SimpleKMeansCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {

        SimpleKMeans cluster = new SimpleKMeans();   // new instance of clusterer
        cluster.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,cluster);
    }
}
