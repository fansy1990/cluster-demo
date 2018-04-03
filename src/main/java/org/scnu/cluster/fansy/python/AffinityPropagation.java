package org.scnu.cluster.fansy.python;

import org.scnu.model.RemoteHost;
import org.scnu.util.ScpTo;

import java.io.File;
import java.util.Map;

import static org.scnu.util.Utils.*;

/**
 * 参数：
 * <input> <output> <splitter> <quantile> <n_samples> <random_state> <min_bin_freq>
 * input: 输入
 * output：输出
 * splitter：输入列分隔符
 * damping : float, optional, default: 0.5
 Damping factor (between 0.5 and 1) is the extent to
 which the current value is maintained relative to
 incoming values (weighted 1 - damping). This in order
 to avoid numerical oscillations when updating these
 values (messages).

 max_iter : int, optional, default: 200
 Maximum number of iterations.

 convergence_iter : int, optional, default: 15
 Number of iterations with no change in the number
 of estimated clusters that stops the convergence.

 preference : array-like, shape (n_samples,) or float, optional
 Preferences for each point - points with larger values of
 preferences are more likely to be chosen as exemplars. The number
 of exemplars, ie of clusters, is influenced by the input
 preferences value. If the preferences are not passed as arguments,
 they will be set to the median of the input similarities.

 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午9:50.
 */
public class AffinityPropagation extends AbstractClusterPython {
    private static final String py_file = "python/py_affinity_propagation.py";
    @Override
    public String prepareCommand(RemoteHost remoteHost,Map<String, String> params) {
        params.put(PYTHON_ALGORITHM_NAME,"Mean Shift");
        //1. upload python script
        String remote_algorithm = PYTHON_PREFIX_PATH+ File.separator+
                "py_affinity_propagation_"+System.currentTimeMillis()+".py";
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
                .append(params.getOrDefault("damping","0.5"))
                .append(" ")
                .append(params.getOrDefault("max_iter","200"))
                .append(" ")
                .append(params.getOrDefault("convergence_iter","15"))
                .append(" ")
                .append(params.getOrDefault("preference","-50"))
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
