package org.scnu.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.scnu.model.RemoteHost;
import org.scnu.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午4:09.
 */
public class SshExec {
    private  static Logger logger = LoggerFactory.getLogger(SshExec.class);
    public static Result run(RemoteHost remoteHost, String command){
        logger.info("command:{}",command);
        Result result = new Result();
        try {
            String user = remoteHost.getUser();
            String host = remoteHost.getIp();
            String password = remoteHost.getPassword();
            int port = remoteHost.getPort();

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");//跳过公钥的询问
            session.setConfig(config);
            session.setPassword(password);
            session.connect(Utils.SSH_TIMEOUT);

            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // X Forwarding
            // channel.setXForwarding(true);

            //channel.setInputStream(System.in);
            channel.setInputStream(null);

            //channel.setOutputStream(System.out);

            //FileOutputStream fos=new FileOutputStream("/tmp/stderr");
            //((ChannelExec)channel).setErrStream(fos);
            ((ChannelExec)channel).setErrStream(System.err);

            InputStream in=channel.getInputStream();

            channel.connect();
            StringBuilder builder = new StringBuilder();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    builder.append(new String(tmp, 0, i))
                    .append("\n");
                }
                if(channel.isClosed()){
                    if(in.available()>0) continue;
                    result.setExitCode(channel.getExitStatus());
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }
                try{Thread.sleep(1000);}catch(Exception ee){}
            }
            channel.disconnect();
            session.disconnect();
//            result.setExitCode(0);
            result.setInformation(builder.toString());
            return result;
        }
        catch(Exception e){
            System.out.println(e);
            result.setExitCode(-1);
            result.setInformation(e.getMessage());
        }
        return result;
    }


}
