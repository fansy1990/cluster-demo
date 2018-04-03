package org.scnu.util;

import org.junit.Assert;
import org.scnu.model.Instance;
import org.scnu.model.RemoteHost;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/8 下午2:20.
 */
public class Utils {

    public static final int SSH_TIMEOUT= 5000;

    public static final String PYTHON_PREFIX_PATH="/tmp/python_workspace";

    public static final String PYTHON_INPUT="input";

    public static final String PYTHON_OUTPUT="output";
    public static final String PYTHON_REMOTE_INPUT="remote_input";

    public static final String PYTHON_REMOTE_OUTPUT="remote_output";

    public static final String PYTHON_ALGORITHM="python_algorithm";
    public static final String PYTHON_ALGORITHM_NAME="python_algorithm_name";

    /**
     * 返回测试输入数据
     * @return
     */
    public static List<Instance> getInputs1(){
        List<Instance> inputs = new ArrayList<Instance>(){
            {
                add(new Instance("1.3,1,2",","));
                add(new Instance("4.0,1.1,2.2",","));
                add(new Instance("3.1,6.2,2.3",","));
                add(new Instance("2.9,4,2.3",","));
                add(new Instance("3.9,1.4,2",","));
                add(new Instance("5.3,5.4,9.6",","));
                add(new Instance("2.3,6.4,5.9",","));
                add(new Instance("5.8,3.4,9.1",","));
                add(new Instance("7.5,4.4,9.2",","));
                add(new Instance("5.5,7.4,9.8",","));
            }
        };
        return inputs;
    }

    /**
     * 获取数据
     * @return
     */
    public static List<Instance> getInputs() throws FileNotFoundException {
        List<Instance> inputs = new ArrayList<>();
        try {
            FileReader fr = new FileReader(Utils.class.getClassLoader().getResource( "kdata.csv").getFile());
            BufferedReader br = new BufferedReader(fr);
            String line ;
            while ((line = br.readLine()) != null) {
                inputs.add(new Instance(line,","));
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputs;
    }

    public static RemoteHost getExampleHost(){
        RemoteHost remoteHost = new RemoteHost("root", "123456", "master", 22);
        return remoteHost;
    }

    public static void justForTest(List<Instance> inputs,List<Instance> results,int k){
        //=============================== 测试
        try {

            Assert.assertNotNull("输出结果不能是Null!",results);
            // 1. 输入输出记录相同
            Assert.assertEquals("输入记录需要和输出记录相同!",inputs.size(), results.size());
            // 2. 输出结果每个都有值，而且在[0,k)
            for (Instance result : results) {
                Assert.assertTrue("标签类必须在[0,k)之间,label: "+result.getLabel(),result.getLabel() >= 0 && result.getLabel() < k);
            }
        }catch (Exception e){
            Assert.assertTrue(e.getMessage(),false);
        }
    }

}
