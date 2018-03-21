package org.scnu.cluster.fansy.weka;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;
import weka.clusterers.EM;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/19 下午9:38.
 */
public class EMCluster implements ClusterInterface {
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception {
        EM em = new EM();
        em.setOptions(WekaUtils.transformMap2StringArray(params));
        return WekaUtils.classify(inputs,em);
    }
}
