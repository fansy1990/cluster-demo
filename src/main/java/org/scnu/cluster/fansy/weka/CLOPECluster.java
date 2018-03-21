package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.CLOPE;
import weka.clusterers.EM;

import java.util.List;
import java.util.Map;

/**
 *
 -R <num>
    Repulsion
    (default 2.6)
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/19 下午9:38.
 */
public class CLOPECluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {
        CLOPE clope = new CLOPE();
        clope.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,clope);
    }
}
