package org.scnu.cluster.fansy;

import org.scnu.model.RemoteHost;

import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午3:20.
 */
public interface ClusterPythonInterface {
    public void runCluster(RemoteHost remoteHost, Map<String, String> params);
}
