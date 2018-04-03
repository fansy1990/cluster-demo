# -*- coding:utf-8 -*-
# python mean shift demo
from sklearn.cluster import Birch
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
        ret_data.append([float(items[j]) for j in range(0,len(items))])
    return ret_data


if __name__ == '__main__':
    file_path_ = sys.argv[1]
    file_out_path = sys.argv[2]
    splitter_ = sys.argv[3]
    branching_factor_ = int(sys.argv[4])
    n_clusters_= int(sys.argv[5])
    threshold_ = float(sys.argv[6])
    #
    # file_path_ = 'kdata.csv'
    # file_out_path = 'out_brc.txt'
    # splitter_ = ','
    # branching_factor_ = 50
    # n_clusters_ = 3
    # threshold_ = 0.5

    data = load_data(file_path_,splitter_)

    brc = Birch(branching_factor=branching_factor_, n_clusters=n_clusters_, threshold=threshold_,compute_labels = True)
    brc.fit(data)
    cluster_centers = brc.subcluster_centers_
    print("centers:")
    for d in cluster_centers:
        print(d)
    label = np.ndarray.tolist(brc.labels_)
    res_data = []
    t = []
    for i in range(0,len(data)):
        t = data[i]
        t.append(label[i])
        res_data.append(t)
    # print(res_data[0])
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
