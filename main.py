from fastapi import FastAPI, HTTPException
from data_loader import load_preference_data
from recommender import CollaborativeFilter

app = FastAPI(title="Music Recommendation Service")

# 初始化模型（启动时加载数据）
print("Loading preference sparse matrix...")
pref_matrix, user_ids, music_ids = load_preference_data() # 接收三个返回值
print(f"Matrix shape: {pref_matrix.shape}")

# 将这三个参数传给推荐器
recommender = CollaborativeFilter(pref_matrix, user_ids, music_ids)
print("Model ready.")
@app.get("/")
async def root():
    return {"message": "Music Recommendation Service is running"}

@app.get("/recommend/user/{user_id}")
async def recommend(user_id: int):
    try:
        recs = recommender.recommend_for_user(user_id)
        return recs
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/similar/music/{music_id}")
async def similar(music_id: int):
    try:
        sims = recommender.find_similar_musics(music_id)
        return sims
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))