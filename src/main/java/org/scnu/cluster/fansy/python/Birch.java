package org.scnu.cluster.fansy.python;

import org.scnu.model.RemoteHost;
import org.scnu.util.ScpTo;

import java.io.File;
import java.util.Map;

import static org.scnu.util.Utils.*;

/**
 * 参数：
 * <input> <output> <splitter> <branching_factor> <n_clusters> <threshold>
 * input: 输入
 * output：输出
 * splitter：输入列分隔符
 *
 * threshold : float, default 0.5
 The radius of the subcluster obtained by merging a new sample and the
 closest subcluster should be lesser than the threshold. Otherwise a new
 subcluster is started. Setting this value to be very low promotes
 splitting and vice-versa.

 branching_factor : int, default 50
 Maximum number of CF subclusters in each node. If a new samples enters
 such that the number of subclusters exceed the branching_factor then
 that node is split into two nodes with the subclusters redistributed
 in each. The parent subcluster of that node is removed and two new
 subclusters are added as parents of the 2 split nodes.

 n_clusters : int, instance of sklearn.cluster model, default 3
 Number of clusters after the final clustering step, which treats the
 subclusters from the leaves as new samples.


 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午9:50.
 */
public class Birch extends AbstractClusterPython {
    private static final String py_file = "python/py_birch.py";
    @Override
    public String prepareCommand(RemoteHost remoteHost,Map<String, String> params) {
        params.put(PYTHON_ALGORITHM_NAME,"Birch");
        //1. upload python script
        String remote_algorithm = PYTHON_PREFIX_PATH+ File.separator+
                "py_birch_"+System.currentTimeMillis()+".py";
        ScpTo.run(remoteHost,remote_algorithm,
                this.getClass().getClassLoader().getResource(py_file).getPath());
        params.put(PYTHON_ALGORITHM,remote_algorithm);
// input,output,splitter,k
        StringBuilder command = new StringBuilder();
        command.append("python ")
                .append(remote_algorithm)
                .append(" ")
                .append(params.get(PYTHON_REMOTE_INPUT))
                .append(" ")
                .append(params.get(PYTHON_REMOTE_OUTPUT))
                .append(" ")
                .append(getSplitter(params.get("splitter")))
                .append(" ")
                .append(params.getOrDefault("branching_factor","50"))
                .append(" ")
                .append(params.getOrDefault("n_clusters","3"))
                .append(" ")
                .append(params.getOrDefault("threshold","0.3"))
            ;

        return command.toString();
    }

    private String getSplitter(String splitter) {

        if(" ".equals(splitter)){
            return "' '";
        }else{
            return splitter;
        }
    }
}
