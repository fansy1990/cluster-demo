# -*- coding:utf-8 -*-
# python Affinity Propagation demo
from sklearn.cluster import AffinityPropagation
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
    damping_ = float(sys.argv[4])
    max_iter_= int(sys.argv[5])
    convergence_iter_ = int(sys.argv[6])
    preference_ = int(sys.argv[7])
    #
    # file_path_ = 'kdata.csv'
    # file_out_path = 'out_affinity_propagation.txt'
    # splitter_ = ','
    # damping_ = 0.5
    # max_iter_= 200
    # convergence_iter_ = 15
    # preference_ = -50

    data = load_data(file_path_,splitter_)

    # Compute Affinity Propagation
    af = AffinityPropagation(damping=damping_,max_iter=max_iter_,convergence_iter= convergence_iter_, preference=preference_).fit(data)
    cluster_centers = af.cluster_centers_
    print("centers:")

    for d in cluster_centers:
        print(d)
    label = np.ndarray.tolist(af.labels_)
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