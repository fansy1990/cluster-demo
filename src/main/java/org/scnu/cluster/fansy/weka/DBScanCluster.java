package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.DBSCAN;
import weka.clusterers.EM;

import java.util.List;
import java.util.Map;

/**
 * -E <double>
    epsilon (default = 0.9)
 -M <int>
    minPoints (default = 6)
 -I <String>
    index (database) used for DBSCAN (default = weka.clusterers.forOPTICSAndDBScan.Databases.SequentialDatabase)
 -D <String>
    distance-type (default = weka.clusterers.forOPTICSAndDBScan.DataObjects.EuclideanDataObject)

 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/19 下午9:38.
 */
public class DBScanCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {
        DBSCAN dbscan = new DBSCAN();
        dbscan.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,dbscan);
    }
}
