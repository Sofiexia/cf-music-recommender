import pandas as pd
from sqlalchemy import create_engine


def load_preference_data():
    # 数据库连接配置（应与 application.yml 一致）
    engine = create_engine('mysql+pymysql://root:666666@localhost:3306/music_db')

    # 获取用户行为，计算偏好分数
    query = """
    SELECT u.user_id, m.music_id, 
           SUM(CASE WHEN ua.action_type = 'like' THEN 2 
                    WHEN ua.action_type = 'collect' THEN 3 
                    WHEN ua.action_type = 'play' THEN 1 
                    ELSE 0 END) as score
    FROM user_actions ua
    JOIN users u ON ua.user_id = u.user_id
    JOIN musics m ON ua.music_id = m.music_id
    GROUP BY u.user_id, m.music_id
    """
    df = pd.read_sql(query, engine)

    # 转换为用户-音乐矩阵（行：用户，列：音乐）
    matrix = df.pivot_table(index='user_id', columns='music_id', values='score', fill_value=0)
    return matrix


if __name__ == "__main__":
    mat = load_preference_data()
    print(mat.shape)