package org.scnu.util;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/29 下午3:44.
 */
import com.jcraft.jsch.*;
import org.scnu.model.RemoteHost;
import org.scnu.model.Result;
import org.slf4j.*;

import java.io.*;

public class ScpFrom{
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ScpTo.class);
    public static Result run(RemoteHost remoteHost, String remoteFile, String localFile){
        logger.info("local:{}\nremote:{}",new Object[]{localFile,remoteFile});
        Result result = new Result();
        FileOutputStream fos=null;
        try{

            String user = remoteHost.getUser();
            String host = remoteHost.getIp();
            String password = remoteHost.getPassword();
            int port = remoteHost.getPort();

            String prefix=null;
            if(new File(localFile).isDirectory()){
                prefix=localFile+File.separator;
            }

            JSch jsch=new JSch();
            Session session=jsch.getSession(user, host, port);

            // username and password will be given via UserInfo interface.
//            UserInfo ui=new MyUserInfo();
//            session.setUserInfo(ui);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");//跳过公钥的询问
            session.setConfig(config);
            session.setPassword(password);
            session.connect(Utils.SSH_TIMEOUT);

            // exec 'scp -f rfile' remotely
            String command="scp -f "+remoteFile;
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out=channel.getOutputStream();
            InputStream in=channel.getInputStream();

            channel.connect();

            byte[] buf=new byte[1024];

            // send '\0'
            buf[0]=0; out.write(buf, 0, 1); out.flush();

            while(true){
                int c=checkAck(in);
                if(c!='C'){
                    break;
                }

                // read '0644 '
                in.read(buf, 0, 5);

                long filesize=0L;
                while(true){
                    if(in.read(buf, 0, 1)<0){
                        // error
                        break;
                    }
                    if(buf[0]==' ')break;
                    filesize=filesize*10L+(long)(buf[0]-'0');
                }

                String file=null;
                for(int i=0;;i++){
                    in.read(buf, i, 1);
                    if(buf[i]==(byte)0x0a){
                        file=new String(buf, 0, i);
                        break;
                    }
                }

                //System.out.println("filesize="+filesize+", file="+file);

                // send '\0'
                buf[0]=0; out.write(buf, 0, 1); out.flush();

                // read a content of lfile
                fos=new FileOutputStream(prefix==null ? localFile : prefix+file);
                int foo;
                while(true){
                    if(buf.length<filesize) foo=buf.length;
                    else foo=(int)filesize;
                    foo=in.read(buf, 0, foo);
                    if(foo<0){
                        // error
                        break;
                    }
                    fos.write(buf, 0, foo);
                    filesize-=foo;
                    if(filesize==0L) break;
                }
                fos.close();
                fos=null;

                if(checkAck(in)!=0){
                    result.setExitCode(0);
                    return result;
                }

                // send '\0'
                buf[0]=0; out.write(buf, 0, 1); out.flush();
            }

            session.disconnect();

//            System.exit(0);
            result.setExitCode(0);
            return result;
        }
        catch(Exception e){
            result.setInformation(e.getMessage());
            result.setExitCode(-1);
            try{
                if(fos!=null)fos.close();
            }catch(Exception ee){}
        }
        return result;
    }

    static int checkAck(InputStream in) throws IOException{
        int b=in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if(b==0) return b;
        if(b==-1) return b;

        if(b==1 || b==2){
            StringBuffer sb=new StringBuffer();
            int c;
            do {
                c=in.read();
                sb.append((char)c);
            }
            while(c!='\n');
            if(b==1){ // error
                System.out.print(sb.toString());
            }
            if(b==2){ // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
    }

}
