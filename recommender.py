import numpy as np
from sklearn.metrics.pairwise import cosine_similarity


class CollaborativeFilter:
    def __init__(self, preference_matrix):
        self.matrix = preference_matrix
        self.user_ids = preference_matrix.index.tolist()
        self.music_ids = preference_matrix.columns.tolist()
        self.user_sim = cosine_similarity(preference_matrix)
        self.music_sim = cosine_similarity(preference_matrix.T)

    def recommend_for_user(self, user_id, top_n=10):
        if user_id not in self.user_ids:
            return []
        idx = self.user_ids.index(user_id)
        sim_scores = self.user_sim[idx]
        # 取最相似的5个用户
        similar_users = np.argsort(-sim_scores)[1:6]
        # 加权平均偏好
        similar_scores = self.matrix.iloc[similar_users].mean(axis=0)
        # 过滤已听过的音乐
        listened = self.matrix.loc[user_id]
        recommended = similar_scores[listened == 0].sort_values(ascending=False).head(top_n)
        return recommended.index.tolist()

    def find_similar_musics(self, music_id, top_n=5):
        if music_id not in self.music_ids:
            return []
        idx = self.music_ids.index(music_id)
        sim_scores = self.music_sim[idx]
        similar = np.argsort(-sim_scores)[1:top_n + 1]
        return [self.music_ids[i] for i in similar]