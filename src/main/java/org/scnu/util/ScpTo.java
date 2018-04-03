package org.scnu.util;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午3:51.
 */

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.scnu.model.RemoteHost;
import org.scnu.model.Result;
import org.slf4j.*;

import java.io.*;

import static org.scnu.util.ScpFrom.*;

public class ScpTo {
    private static Logger logger = LoggerFactory.getLogger(ScpTo.class);
    public static Result run(RemoteHost remoteHost, String remoteFile, String localFile) {
        logger.info("local:{}\nremote:{}",new Object[]{localFile,remoteFile});
        Result result = new Result();
        FileInputStream fis = null;
        try {

            String user = remoteHost.getUser();
            String host = remoteHost.getIp();
            String password = remoteHost.getPassword();
            int port = remoteHost.getPort();

            String prefix = null;
            if (new File(localFile).isDirectory()) {
                prefix = localFile + File.separator;
            }

            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");//跳过公钥的询问
            session.setConfig(config);
            session.setPassword(password);
            session.connect(Utils.SSH_TIMEOUT);

            boolean ptimestamp = true;

            // exec 'scp -t rfile' remotely
            String command = "scp " + (ptimestamp ? "-p" : "") + " -t " + remoteFile;
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();

            if (checkAck(in) != 0) {
                result.setExitCode(-1);
                return result;
            }

            File _lfile = new File(localFile);

            if (ptimestamp) {
                command = "T" + (_lfile.lastModified() / 1000) + " 0";
                // The access time should be sent here,
                // but it is not accessible with JavaAPI ;-<
                command += (" " + (_lfile.lastModified() / 1000) + " 0\n");
                out.write(command.getBytes());
                out.flush();
                if (checkAck(in) != 0) {
                    result.setExitCode(-1);
                    return result;
                }
            }

            // send "C0644 filesize filename", where filename should not include '/'
            long filesize = _lfile.length();
            command = "C0644 " + filesize + " ";
            if (localFile.lastIndexOf('/') > 0) {
                command += localFile.substring(localFile.lastIndexOf('/') + 1);
            } else {
                command += localFile;
            }
            command += "\n";
            out.write(command.getBytes());
            out.flush();
            if (checkAck(in) != 0) {
                result.setExitCode(-1);
                return result;
            }

            // send a content of lfile
            fis = new FileInputStream(localFile);
            byte[] buf = new byte[1024];
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis = null;
            // send '\0'
            buf[0] = 0;
            out.write(buf, 0, 1);
            out.flush();
            if (checkAck(in) != 0) {
                result.setExitCode(-1);
                return result;
            }
            out.close();

            channel.disconnect();
            session.disconnect();

//            System.exit(0);
            result.setExitCode(0);
            return result;
        } catch (Exception e) {
            result.setInformation(e.getMessage());
            result.setExitCode(-1);
            try {
                if (fis != null) fis.close();
            } catch (Exception ee) {
            }
        }
        return  result;
    }

}