import numpy as np
from sklearn.metrics.pairwise import cosine_similarity


class CollaborativeFilter:
    def __init__(self, sparse_matrix, user_ids, music_ids):
        self.matrix = sparse_matrix
        self.user_ids = user_ids
        self.music_ids = music_ids

        # sklearn 的 cosine_similarity 原生完美支持 scipy 稀疏矩阵，速度飞快
        self.user_sim = cosine_similarity(self.matrix)

        #【新增】计算音乐相似度（Item-Item)
        #对矩阵转置，将音乐作为行进行余弦相似度计算
        self.music_sim = cosine_similarity(self.matrix.T)

    def recommend_for_user(self, user_id, top_n=10):
        if user_id not in self.user_ids:
            return []  # 冷启动兜底可以加在这里

        # 1. 找到该用户在矩阵中的行索引
        idx = self.user_ids.index(user_id)
        sim_scores = self.user_sim[idx]

        # 2. 取最相似的5个用户 (排除自己)
        # np.argsort 是升序，[::-1] 反转为降序，[1:6] 跳过第0个(自己)
        similar_users_idx = np.argsort(sim_scores)[::-1][1:6]

        # 3. 计算这5个邻居对所有歌曲的加权平均偏好
        # 对稀疏矩阵切片并求平均，.A1 是将其转换为 1 维 NumPy 数组
        similar_scores = self.matrix[similar_users_idx].mean(axis=0).A1

        # 4. 获取当前用户已听过的音乐记录
        listened = self.matrix[idx].toarray()[0]

        # 5. 过滤掉已听过的音乐，找出推荐候选
        # np.where(listened == 0)[0] 找出没听过的音乐列索引
        candidate_indices = np.where(listened == 0)[0]

        # 获取候选歌曲的分数
        candidate_scores = similar_scores[candidate_indices]

        # 6. 对候选歌曲按分数降序排列，取 Top N
        # argsort 得到的是候选列表里的相对索引
        top_relative_indices = np.argsort(candidate_scores)[::-1][:top_n]

        # 映射回全局的音乐列索引
        top_music_indices = candidate_indices[top_relative_indices]

        # 7. 将列索引转换为数据库里真实的 music_id
        recommended_music_ids = [self.music_ids[i] for i in top_music_indices]

        return recommended_music_ids

    def find_similar_musics(self, music_id, top_n=5):
        """
        根据音乐ID寻找相似音乐
        """
        # 1. 验证音乐是否存在
        if music_id not in self.music_ids:
            return []

        # 2. 找到该音乐在矩阵中的列索引
        idx = self.music_ids.index(music_id)

        # 3. 从物品相似度矩阵中提取该音乐的得分向量
        sim_scores = self.music_sim[idx]

        # 4. 排序并取 Top N
        # np.argsort 返回升序索引，[::-1] 翻转为降序[cite: 3]
        # [1:top_n+1] 是为了跳过它自己（自己和自己的相似度永远是1.0）
        similar_indices = np.argsort(sim_scores)[::-1][1:top_n + 1]

        # 5. 将索引映射回真实的数据库 music_id
        return [self.music_ids[i] for i in similar_indices]