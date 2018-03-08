package org.scnu.cluster;

import org.scnu.model.Instance;

import java.util.List;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/8 下午1:08.
 */
public interface ClusterInterface {
    /**
     * 算法调用接口
     * @param inputs 输入数据
     * @param params 算法参数
     * @return 聚类后数据
     */
    List<Instance> runCluster(List<Instance> inputs, Map params) throws Exception;


}
