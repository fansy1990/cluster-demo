package org.scnu.cluster.fansy.python;

import org.scnu.cluster.fansy.ClusterPythonInterface;
import org.scnu.model.RemoteHost;
import org.scnu.model.Result;
import org.scnu.util.ScpFrom;
import org.scnu.util.ScpTo;
import org.scnu.util.SshExec;
import static org.scnu.util.Utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午3:27.
 */
public abstract  class AbstractClusterPython implements ClusterPythonInterface{
    private  Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public  void runCluster( RemoteHost remoteHost,
                                    Map<String,String> params){

        // 0. 处理参数
        String inputStr = params.get(PYTHON_INPUT);
        String outputStr = params.get(PYTHON_OUTPUT);

        String remoteInputFile = getRemoteFile(inputStr);
        String remoteOutputFile = getRemoteFile(outputStr);


        params.put(PYTHON_REMOTE_INPUT,remoteInputFile);
        params.put(PYTHON_REMOTE_OUTPUT,remoteOutputFile);

        Result result = null;
        // 1. 上传文件
        result = ScpTo.run(remoteHost,remoteInputFile,inputStr);// 上传输入
        if(result.getExitCode() != 0){
            logger.warn("上传文件失败！, 错误信息：{}",result.getInformation());
            return;
        }
        logger.info("上传文件成功!\n"+result.getInformation());
        // 2. 拼接命令，执行命令
        String command = prepareCommand(remoteHost,params);
        result = SshExec.run(remoteHost,command);

        if(result.getExitCode() != 0){
            logger.warn("任务失败！,命令：{}\n 错误信息：{}",
                    new Object[]{command,result.getInformation()});
            destroy(remoteHost,params);
            return;
        }else{// 回传数据
            logger.info(result.getInformation());
            result = ScpFrom.run(remoteHost,remoteOutputFile,outputStr);
            if(result.getExitCode() != 0){
                logger.warn("回传输出失败！, 错误信息：{}",result.getInformation());
                destroy(remoteHost,params);
                return;
            }
            logger.info(result.getInformation());
        }

        // 3. 删除文件
        result = destroy(remoteHost,params);
        if(result.getExitCode() != 0){
            logger.warn("文件删除失败！, 错误信息：{}",result.getInformation());
            return;
        }
        logger.info(result.getInformation());
        logger.info("算法:{} ,运行完成！",params.get(PYTHON_ALGORITHM_NAME));
    }

    /**
     * 删除远程输入／输出／算法文件
     * @param remoteHost
     * @param params
     */
    private Result destroy(RemoteHost remoteHost,Map<String, String> params) {
        Result result = null;
        String preCommand= " rm -rf ";

        StringBuilder builder = new StringBuilder();
        builder.append(preCommand)
                .append(params.get(PYTHON_REMOTE_OUTPUT)).append(";")
                .append(preCommand)
                .append(params.get(PYTHON_REMOTE_INPUT)).append(";")
                .append(preCommand)
                .append(params.get(PYTHON_ALGORITHM));


        result = SshExec.run(remoteHost,builder.toString() );

        return result;
    }

    protected  String getRemoteFile(String path){
        try {
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        int index = path.lastIndexOf(File.separatorChar);
        int index_ = path.lastIndexOf(".");
        return PYTHON_PREFIX_PATH + File.separator+path.substring(index + 1,index_)+
               "_"+ System.currentTimeMillis()+ path.substring(index_);
    }

    public abstract  String prepareCommand(RemoteHost remoteHost,Map<String,String >params);
}
