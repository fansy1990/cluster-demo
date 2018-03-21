package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.HierarchicalClusterer;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  参数：
 -D
     If set, classifier is run in debug mode and
     may output additional info to the console
 -B
     If set, distance is interpreted as branch length
     otherwise it is node height.
 -N <Nr Of Clusters>
    number of clusters
 -P
    Flag to indicate the cluster should be printed in Newick format.
 -L [SINGLE|COMPLETE|AVERAGE|MEAN|CENTROID|WARD|ADJCOMLPETE|NEIGHBOR_JOINING]
    Link type (Single, Complete, Average, Mean, Centroid, Ward, Adjusted complete, Neighbor joining)
 -A <classname and options>
    Distance function to use.
    (default: weka.core.EuclideanDistance)


 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/21 下午9:38.
 */
public class HierarchicalCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {

        HierarchicalClusterer cluster = new HierarchicalClusterer();   // new instance of clusterer
        cluster.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,cluster);
    }
}
