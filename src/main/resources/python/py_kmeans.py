# -*- coding:utf-8 -*-
# python kmeans demo
from sklearn.cluster import KMeans
import numpy as np
import os
import sys


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
    # file_path_ = 'data.txt'
    file_path_ = sys.argv[1]
    # file_out_path = 'out.txt'
    file_out_path = sys.argv[2]
    # splitter_ = ' '
    splitter_ = sys.argv[3]
    k = int(sys.argv[4])
    data = load_data(file_path_,splitter_)
    km = KMeans(n_clusters=k)
    label = np.ndarray.tolist(km.fit_predict(data))
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
        print( 'delete ',file_out_path)
        os.remove(file_out_path)
    # write to file
    with open(file_out_path, 'w') as file:
        for res_d in res_data:
            file.write(splitter_.join(str(e) for e in res_d))
            file.write('\n')
    print ('save file done！')
