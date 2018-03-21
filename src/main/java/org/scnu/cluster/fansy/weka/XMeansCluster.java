package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.SimpleKMeans;
import weka.clusterers.XMeans;

import java.util.List;
import java.util.Map;

/**
 *  参数：
 -I <num>
    maximum number of overall iterations
    (default 1).
 -M <num>
    maximum number of iterations in the kMeans loop in
    the Improve-Parameter part
    (default 1000).
 -J <num>
    maximum number of iterations in the kMeans loop
    for the splitted centroids in the Improve-Structure part
    (default 1000).
 -L <num>
    minimum number of clusters
    (default 2).
 -H <num>
    maximum number of clusters
 (default 4).
 -B <value>
    distance value for binary attributes
    (default 1.0).
 -use-kdtree
    Uses the KDTree internally
    (default no).
 -K <KDTree class specification>
    Full class name of KDTree class to use, followed
    by scheme options.
    eg: "weka.core.neighboursearch.kdtrees.KDTree -P"
    (default no KDTree class used).
 -C <value>
    cutoff factor, takes the given percentage of the splitted
    centroids if none of the children win
    (default 0.0).
 -D <distance function class specification>
    Full class name of Distance function class to use, followed
    by scheme options.
    (default weka.core.EuclideanDistance).
 -N <file name>
    file to read starting centers from (ARFF format).
 -O <file name>
    file to write centers to (ARFF format).
 -U <int>
    The debug level.
    (default 0)
 -Y <file name>
    The debug vectors file.
 -S <num>
    Random number seed.
    (default 10)

 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/20 下午9:38.
 */
public class XMeansCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {

        XMeans cluster = new XMeans();   // new instance of clusterer
        cluster.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,cluster);
    }
}
