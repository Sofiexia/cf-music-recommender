from contextlib import asynccontextmanager

from fastapi import FastAPI, HTTPException, BackgroundTasks
from data_loader import load_preference_data
from recommender import CollaborativeFilter
import datetime

@asynccontextmanager
async def lifespan(app: FastAPI):
    app.state.model_context = {
        "recommender": None,
        "last_updated": None
    }
    reload_model_task()
    yield

app = FastAPI(title="Music Recommendation Service", lifespan=lifespan)

# 2. 抽离出一个加载/更新模型的独立函数
def reload_model_task():
    try:
        print(f"[{datetime.datetime.now()}] 正在从数据库重新加载偏好矩阵...")
        # 注意：这里假设你已经按照之前的建议改成了稀疏矩阵
        pref_matrix, user_ids, music_ids = load_preference_data()

        # 重新实例化推荐器
        new_recommender = CollaborativeFilter(pref_matrix, user_ids, music_ids)

        # 替换全局变量中的旧模型
        app.state.model_context["recommender"] = new_recommender
        app.state.model_context["last_updated"] = datetime.datetime.now()

        print("模型热更新完成！")
    except Exception as e:
        print(f"模型更新失败: {e}")


# 4. 新增一个供 Java 端调用的刷新接口
@app.post("/refresh")
async def refresh_model(background_tasks: BackgroundTasks):
    # 将加载任务扔到后台执行，立即给 Java 端返回 200，不阻塞业务
    background_tasks.add_task(reload_model_task)
    return {"status": "success", "message": "已触发后台模型更新任务"}


# 5. 修改你原有的推荐接口，从全局字典里取模型
@app.get("/recommend/user/{user_id}")
async def recommend_for_user(user_id: str):
    recommender = app.state.model_context["recommender"]
    if not recommender:
        raise HTTPException(status_code=503, detail="模型尚未准备好")

    try:
        recs = recommender.recommend_for_user(user_id)
        return {"user_id": user_id, "recommendations": recs}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/recommend/music/{music_id}")
async def similar_music(music_id: str):
    recommender = app.state.model_context["recommender"]
    try:
        # 直接调用 recommender 实例的方法
        sims = recommender.find_similar_musics(music_id)
        return {"music_id": music_id, "similar_musics": sims}
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))