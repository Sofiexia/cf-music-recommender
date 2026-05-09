import pandas as pd
from sqlalchemy import create_engine

# 1. 数据库配置
db_url = "mysql+pymysql://root:666666@localhost:3306/music_db"
engine = create_engine(db_url)


def start_import():
    print("正在读取 Music Info.csv...")
    # 先取 5000 首歌曲作为曲库
    df_music = pd.read_csv(r"D:\HNUST\GraduationProject\project\archive\Music Info.csv", nrows=5000)

    # 2. 映射 musics 表字段
    musics_to_db = df_music[['track_id', 'name', 'artist', 'genre', 'tags', 'spotify_preview_url']].copy()
    musics_to_db.columns = ['music_id', 'title', 'artist', 'genre', 'tags', 'url']

    print("正在读取 User Listening History.csv...")
    # 读取 20000 条行为数据#
    df_history = pd.read_csv(r"D:\HNUST\GraduationProject\project\archive\User Listening History.csv", nrows=20000)

    # 3. 数据清洗：只保留在音乐表里存在的 track_id
    df_history = df_history[df_history['track_id'].isin(musics_to_db['music_id'])]

    # 4. 映射 user_actions 表字段
    actions_to_db = df_history[['user_id', 'track_id', 'playcount']].copy()
    actions_to_db.columns = ['user_id', 'music_id', 'play_count']
    actions_to_db['action_type'] = 'play'  # 统一打上“播放”标签

    # 5. 生成对应的 users 表数据 (这样你才能用这些用户登录)
    unique_users = df_history['user_id'].unique()
    users_to_db = pd.DataFrame(unique_users, columns=['user_id'])
    users_to_db['username'] = users_to_db['user_id'].str[:10]  # 取前10位做用户名
    users_to_db['password'] = '$2a$10$8.UnVuG9HHgffUDAlk8q2OuVGkqEnLPz0WpOkvF7tK9vH4b6.LpLq' # 默认初始密码
    users_to_db['user_type'] = 0  # 普通用户类型

    print(f"写入数据库: {len(musics_to_db)}首歌, {len(users_to_db)}个用户...")

    # 顺序：先存音乐和用户，再存关联记录
    musics_to_db.to_sql('musics', con=engine, if_exists='append', index=False)
    users_to_db.to_sql('users', con=engine, if_exists='append', index=False)
    actions_to_db.to_sql('user_actions', con=engine, if_exists='append', index=False)

    print("数据集已成功导入并打通。")


if __name__ == "__main__":
    start_import()