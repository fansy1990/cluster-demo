package org.scnu.util;

import org.junit.Assert;
import org.scnu.model.Instance;

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
    /**
     * 返回测试输入数据
     * @return
     */
    public static List<Instance> getInputs(){
        List<Instance> inputs = new ArrayList<Instance>(){
            {
                add(new Instance("0,1,2",","));
                add(new Instance("0.0,1.1,2",","));
                add(new Instance("0.1,1.2,2",","));
                add(new Instance("0,1,2.3",","));
                add(new Instance("0,1.4,2",","));
                add(new Instance("5,5.4,9",","));
                add(new Instance("5.3,5.4,9",","));
                add(new Instance("5,5.4,9",","));
                add(new Instance("5,4.4,9",","));
                add(new Instance("5,7.4,9.8",","));
            }
        };
        return inputs;
    }


    public static void justForTest(List<Instance> inputs,List<Instance> results,int k){
        //=============================== 测试
        try {

            Assert.assertNotNull("输出结果不能是Null!",results);
            // 1. 输入输出记录相同
            Assert.assertEquals("输入记录需要和输出记录相同!",inputs.size(), results.size());
            // 2. 输出结果每个都有值，而且在[0,k)
            for (Instance result : results) {
                Assert.assertTrue("标签类必须在[0,k)之间!",result.getLabel() >= 0 && result.getLabel() < k);
            }
        }catch (Exception e){
            Assert.assertTrue(e.getMessage(),false);
        }
    }
}
