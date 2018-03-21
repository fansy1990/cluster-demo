package org.scnu.cluster.fansy.weka;

import sun.jvm.hotspot.debugger.win32.coff.OptionalHeader;
import weka.clusterers.AbstractClusterer;
import weka.clusterers.EM;
import weka.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/19 下午9:41.
 */
public class WekaUtils {
    /**
     * transform the given data to weka Instances
     * @param inputs
     * @return
     */
    public static Instances transform(List<org.scnu.model.Instance> inputs){
        int size = inputs.get(0).getData().length;
        FastVector fastVector = new FastVector();
        for(int i =0 ;i< size ;i++) {
            fastVector.addElement(new Attribute("x"+(i+1)));
        }
        Instances instances = new Instances("tmp_"+System.currentTimeMillis(),fastVector,size);
        for(org.scnu.model.Instance input:inputs) {
            instances.add(new Instance(1.0, input.getData()));
//            instances.add(new Instance(1.0, new double[]{1.0, 2.0, 3.0, 4.1}));
        }
        System.out.println("last line : "+instances.lastInstance());
        return  instances;
    }

    /**
     * 处理参数
     * @param params
     * @return
     */
    public static String[] transformMap2StringArray(Map params){
        List<String> options = new ArrayList<>();
        int i =0;
        for(Object ks : params.keySet()){
            options.add( ks.toString());
            if(params.get(ks)!= null) {
                options.add(params.get(ks).toString());
            }
        }

        return options.toArray(new String[0]);
    }

    /**
     * 根据模型分类数据
     * @param inputs
     * @param cluster
     * @return
     * @throws Exception
     */
    public static List<org.scnu.model.Instance> classify(List<org.scnu.model.Instance> inputs,
                                                 AbstractClusterer cluster) throws Exception {
        Instances instances = WekaUtils.transform(inputs);
        cluster.buildClusterer(instances);    // build the clusterer
        System.out.println(cluster.toString());
        List<org.scnu.model.Instance> result = new ArrayList<>();

        for(int i = 0 ; i< instances.numInstances(); i ++){
            result.add(new org.scnu.model.Instance( instances.instance(i).toDoubleArray(),
                    cluster.clusterInstance(instances.instance(i))));
        }
        return result;
    }

    public static void main(String[] args){
//        WekaUtils.transform();
    }
}
