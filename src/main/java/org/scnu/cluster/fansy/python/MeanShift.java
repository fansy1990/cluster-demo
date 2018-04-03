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
 * quantile：float, default 0.3,should be between [0, 1], 0.5 means that the median of all pairwise distances is used
 * n_samples: The number of samples to use
 * random_state: 随机数
 * min_bin_freq: To speed up the algorithm, accept only those bins with at least min_bin_freq points as seeds
 *
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午9:50.
 */
public class MeanShift extends AbstractClusterPython {
    private static final String py_file = "python/py_meanshift.py";
    @Override
    public String prepareCommand(RemoteHost remoteHost,Map<String, String> params) {
        params.put(PYTHON_ALGORITHM_NAME,"Mean Shift");
        //1. upload python script
        String remote_algorithm = PYTHON_PREFIX_PATH+ File.separator+
                "py_meanshift_"+System.currentTimeMillis()+".py";
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
                .append(params.getOrDefault("quantile","0.3"))
                .append(" ")
                .append(params.getOrDefault("n_samples","100"))
                .append(" ")
                .append(params.getOrDefault("random_state","10"))
                .append(" ")
                .append(params.getOrDefault("min_bin_freq","1"))
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
