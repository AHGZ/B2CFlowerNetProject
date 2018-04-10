package com.android.p2pflowernet.project.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @描述: 测试数据
 * @创建人：zhangpeisen
 * @创建时间：2017/10/17 下午2:01
 * @修改人：zhangpeisen
 * @修改时间：2017/10/17 下午2:01
 * @修改备注：
 * @throws
 */
public class DateInfo {

    public JSONObject DateInfo() {
        //定义假数据
        JSONObject jsonObjects = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("attr_name", "价格");
            JSONArray jsonArray1 = new JSONArray();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("attr_value", "799");
            jsonObject1.put("img_url", "http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg");
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("attr_value", "399");
            jsonObject2.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("attr_value", "599");
            jsonObject3.put("img_url", "http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg");
            JSONObject jsonObject4 = new JSONObject();
            jsonObject4.put("attr_value", "299");
            jsonObject4.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject5 = new JSONObject();
            jsonObject5.put("attr_value", "299");
            jsonObject5.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            jsonArray1.put(jsonObject1);
            jsonArray1.put(jsonObject2);
            jsonArray1.put(jsonObject3);
            jsonArray1.put(jsonObject4);
            jsonArray1.put(jsonObject5);
            jsonObject.put("attr_list", jsonArray1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("attr_name", "颜色");
            JSONArray jsonArray1 = new JSONArray();
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("attr_value", "赤色");
            jsonObject2.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject3 = new JSONObject();
            jsonObject3.put("attr_value", "橙色");
            jsonObject3.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject4 = new JSONObject();
            jsonObject4.put("attr_value", "黄色");
            jsonObject4.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject5 = new JSONObject();
            jsonObject5.put("attr_value", "绿色");
            jsonObject5.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject6 = new JSONObject();
            jsonObject6.put("attr_value", "青色");
            jsonObject6.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject7 = new JSONObject();
            jsonObject7.put("attr_value", "蓝色");
            jsonObject7.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            JSONObject jsonObject8 = new JSONObject();
            jsonObject8.put("attr_value", "紫色");
            jsonObject8.put("img_url", "http://img2.hqbcdn.com/product/00/76/0076cedb0a7d728ec1c8ec149cff0d16.jpg");
            jsonArray1.put(jsonObject2);
            jsonArray1.put(jsonObject3);
            jsonArray1.put(jsonObject4);
            jsonArray1.put(jsonObject5);
            jsonArray1.put(jsonObject6);
            jsonArray1.put(jsonObject7);
            jsonArray1.put(jsonObject8);
            jsonObject1.put("attr_list", jsonArray1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(jsonObject);
        jsonArray.put(jsonObject1);

        try {
            jsonObjects.put("goods_types", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjects;
    }
}
