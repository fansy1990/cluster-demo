# -*- coding:utf-8 -*-
# python kmeans demo
from sklearn.cluster import KMeans
import numpy as np
import os


def load_data(file_path, splitter=","):
    """
    返回特征向量数组
    Args:
        file_path: 文件路径
        splitter: 分隔符

    Returns:
        特征向量数组
    """
    fr = open(file_path,'r+')
    lines = fr.readlines()
    ret_data = []
    for line in lines:
        items = line.strip().split(splitter)
        ret_data.append([float(items[i]) for i in range(0,len(items))])
    return ret_data


if __name__ == '__main__':
    file_path_ = 'data.txt'
    splitter_ = ' '
    file_out_path = 'out.txt'
    data = load_data(file_path_,splitter_)
    km = KMeans(n_clusters=2)
    label= np.ndarray.tolist(km.fit_predict(data))
    # label = np.ndarry.tolist(km.fit_predict(data))
    res_data = []
    t = []
    for i in range(0,len(data)):
        t = data[i]
        t.append(label[i])
        res_data.append(t)
    print(res_data[0])
    # 判断文件是否存在，存在则删除
    if os.path.exists(file_out_path):
        print 'delete ',file_out_path
        os.remove(file_out_path)
    # write to file
    with open(file_out_path, 'w') as file:
        for res_d in res_data:
            file.write(splitter_.join(str(e) for e in res_d))
            file.write('\n')
    print 'save file done！'
