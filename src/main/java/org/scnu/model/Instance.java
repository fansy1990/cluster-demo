package org.scnu.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fanzhe
 * @email fansy1990@foxmail.com
 * @date 2018/3/8 下午1:44.
 */
public class Instance implements Cloneable {
    private static final Logger log = LoggerFactory.getLogger(Instance.class);

    public double[] getData() {
        return data;
    }

    double[] data = null;

    public int getLabel() {
        return label;
    }

    int label;

    public Instance(double[] data) {
        this.data = data;
    }

    public Instance(String dataStr, String splitter) {
        String[] dataStrArr = dataStr.split(splitter, -1);
        this.data = new double[dataStrArr.length];
        for (int i = 0; i < dataStrArr.length; i++) {
            try {
                data[i] = Double.parseDouble(dataStrArr[i].trim());
            } catch (NumberFormatException e) {
                data[i] = 0.0;
                log.warn("数据转换异常：{}", dataStr);
            }
        }
    }

    public Instance(double[] data, int label) {
        this.data = data;
        this.label = label;
    }

    public Instance addLabel(int label) throws CloneNotSupportedException {
        Instance t = this.clone();
        t.label = label;
        return t;
    }

    @Override
    protected Instance clone() throws CloneNotSupportedException {
        double[] cloneData = new double[this.data.length];
        for (int i = 0; i < this.data.length; i++) {
            cloneData[i] = this.data[i];
        }
        return new Instance(cloneData, this.label);
    }
}
