package org.scnu.cluster.fansy;

import org.scnu.cluster.ClusterInterface;
import org.scnu.model.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 该算法是测试算法，不代表实际算法
 *
 * 算法解释：
 * 该算法把输入分为2聚类，每个类别依次标记为0，1
 *
 * 算法参数：
 * percents : 第一个聚类的比例，如0.2，不大于1
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/8 下午2:01.
 */
public class FrontBackCluster implements ClusterInterface {
    private static final String PERCENTS = "percents" ;

    private String percentStr;

    private int frontCount ;
    @Override
    public List<Instance> runCluster(List<Instance> inputs, Map params) throws CloneNotSupportedException {
        // 1. 初始化参数
        init(inputs.size(),params);

        // 2. 执行聚类建模以及分类预测
        List<Instance> result = new ArrayList<>();
        int i = 0;
        for(Instance one :inputs){
            if(++i < frontCount){
                result.add(one.addLabel(0));
            }else{
                result.add(one.addLabel(1));
            }

        }

        // 3. 返回结果
        return result;
    }

    /**
     * 初始化参数
     * @param params
     */
    private void init(int inputSize,Map params) {
        try{
            percentStr = (String) params.get(PERCENTS);
            frontCount = (int) (inputSize * Double.parseDouble(percentStr));
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }


}
