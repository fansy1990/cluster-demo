package org.scnu.cluster.fansy.python;

import org.scnu.model.RemoteHost;
import org.scnu.util.ScpTo;
import static org.scnu.util.Utils.*;

import java.io.File;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午9:50.
 */
public class KMeans extends AbstractClusterPython {
    private static final String py_file = "python/py_kmeans.py";
    @Override
    public String prepareCommand(RemoteHost remoteHost,Map<String, String> params) {
        params.put(PYTHON_ALGORITHM_NAME,"KMeans");
        //1. upload python script
        String remote_algorithm = PYTHON_PREFIX_PATH+ File.separator+
                "py_kmeans_"+System.currentTimeMillis()+".py";
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
                .append(params.getOrDefault("k","1"))
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
