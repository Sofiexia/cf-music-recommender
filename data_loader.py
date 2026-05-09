import pandas as pd
import numpy as np
from scipy.sparse import csr_matrix
from sqlalchemy import create_engine

def load_preference_data():
    engine = create_engine('mysql+pymysql://root:666666@localhost:3306/music_db')

    # 获取用户行为，计算偏好分数
    query = """
    SELECT u.user_id, m.music_id, 
           SUM(CASE WHEN ua.action_type = 'like' THEN 2 
                    WHEN ua.action_type = 'collect' THEN 3 
                    WHEN ua.action_type = 'play' THEN 1 *ua.play_count
                    ELSE 0 END) as score
    FROM user_actions ua
    JOIN users u ON ua.user_id = u.user_id
    JOIN musics m ON ua.music_id = m.music_id
    GROUP BY u.user_id, m.music_id
    """
    df = pd.read_sql(query, engine)

    # 如果数据库里查不到数据，做个保护机制
    if df.empty:
        print("警告：从数据库中没有查到任何用户行为数据！")
        return None, [], []
    # 1. 提取独立的用户ID和音乐ID列表 (用于后期将索引映射回真实ID)
    user_ids = df['user_id'].astype('category').cat.categories.tolist()
    music_ids = df['music_id'].astype('category').cat.categories.tolist()

    # 2. 将真实的ID映射为从 0 开始的连续索引 (行号和列号)
    row_idx = df['user_id'].astype('category').cat.codes
    col_idx = df['music_id'].astype('category').cat.codes

    # 3. 构建 scipy.sparse CSR 稀疏矩阵
    # csr_matrix((数据, (行索引, 列索引)), 形状=(用户数, 音乐数))
    sparse_matrix = csr_matrix(
        (df['score'], (row_idx, col_idx)),
        shape=(len(user_ids), len(music_ids))
    )

    # 返回稀疏矩阵，以及对应的用户和音乐ID映射表
    return sparse_matrix, user_ids, music_ids

if __name__ == "__main__":
    mat, u_ids, m_ids = load_preference_data()
    print(f"稀疏矩阵形状: {mat.shape}")
    print(f"实际存储的数据点数量(非零元素): {mat.nnz}") # 这就是省内存的秘密