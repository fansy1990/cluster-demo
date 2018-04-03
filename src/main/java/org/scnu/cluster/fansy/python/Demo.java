package org.scnu.cluster.fansy.python;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.python.util.PythonInterpreter;

import java.io.*;
import java.util.Properties;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/27 下午12:53.
 */
public class Demo {
    public static void main(String[] args) throws JSchException {

//        init();
//        test2();
        test1();
    }

    public static void test1() throws JSchException {
//                /Users/fanzhe/PycharmProjects/cluster_demo/venv/bin/python
        String file = "/tmp/pycharm_project_628/demo2/kmeans_demo.py";
        String exec = exec("master", "root", "123456", 22,
                " source /etc/profile; python "+file);
        System.out.println(exec);
    }

    public static String exec(String host,String user,String psw,int port,String command) throws JSchException {
        StringBuffer buff= new StringBuffer();
        Session session =null;
        ChannelExec openChannel =null;
        try {
            JSch jsch=new JSch();
            session = jsch.getSession(user, host, port);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");//跳过公钥的询问
            session.setConfig(config);
            session.setPassword(psw);
            session.connect(5000);//设置连接的超时时间
            openChannel = (ChannelExec) session.openChannel("exec");
            openChannel.setCommand(command); //执行命令
//            int exitStatus = openChannel.getExitStatus(); //退出状态为-1，直到通道关闭
//            System.out.println(exitStatus);

// 下面是得到输出的内容
            openChannel.connect();
//            InputStream in = openChannel.getInputStream();
            InputStream in = openChannel.getErrStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String buf = null;
            while ((buf = reader.readLine()) != null) {
                buff.append(buf+"\n");
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            buff.append(e.getMessage()+"\n");
        } finally{
            if(openChannel!=null&&!openChannel.isClosed()){
                openChannel.disconnect();
            }
            if(session!=null&&session.isConnected()){
                session.disconnect();
            }
        }
        return buff.toString();
    }
    public static void test2(){
        // 执行脚本
        PythonInterpreter interpreter = new PythonInterpreter();
        File file = new File(
                "/Users/fanzhe/PycharmProjects/cluster_demo/demo2/kmeans_demo.py");
        interpreter.execfile(file.getAbsolutePath());
    }
    private static void init(){
        Properties props = new Properties();
//        props.put("python.home", "path to the Lib folder");
        props.put("python.path", "/Users/fanzhe/PycharmProjects/cluster_demo/venv");
        props.put("python.console.encoding", "UTF-8");
        props.put("python.security.respectJavaAccessibility", "false");
        props.put("python.import.site", "false");
        Properties preprops = System.getProperties();
        PythonInterpreter.initialize(preprops, props, new String[0]);
    }
}
