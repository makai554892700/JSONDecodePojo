package com.mayousheng.www.basepojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * pojo基本抽象类，继承此类后方可利用此类的方法
 * Created by marking on 2017/3/24.
 */

public abstract class BasePoJo {

    //带参构造，用于json string直接转对象
    public BasePoJo(String jsonStr) {
        if (jsonStr == null || jsonStr.isEmpty()) {
            return;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (Exception e) {
        }
        if (jsonObject == null) {
            return;
        }
        Class clazz = this.getClass();
        while (BasePoJo.class != clazz) {
            initField(jsonObject, clazz);
            clazz = clazz.getSuperclass();
        }
    }

    private void initField(JSONObject jsonObject, Class clazz) {
        if (clazz == null) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        if (fields == null) {
            return;
        }
        for (Field field : fields) {
            FieldDesc fieldDescs = field.getAnnotation(FieldDesc.class);
            if (fieldDescs == null) {
                continue;
            }//以上代码均为健壮性考虑
            Class fieldType = field.getType();
            String key = fieldDescs.key();
            Class arrayType = fieldDescs.arrayType();
            Object fieldObj;
            if (fieldType == String.class) {
                //根据属性不同类型调用不同方法解析，同时不影响继续解析
                //目前可能存在考虑不周的地方可根据自己的需求再另行更改
                fieldObj = jsonObject.optString(key);
            } else if (fieldType == int.class || fieldType == Integer.class) {
                fieldObj = jsonObject.optInt(key);
            } else if (fieldType == long.class || fieldType == Long.class) {
                fieldObj = jsonObject.optLong(key);
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                fieldObj = jsonObject.optBoolean(key);
            } else if (fieldType == float.class || fieldType == Float.class) {
                fieldObj = jsonObject.optDouble(key);
            } else if (fieldType == double.class || fieldType == Double.class) {
                fieldObj = jsonObject.optDouble(key);
            } else if (ArrayList.class == fieldType) {
                fieldObj = JSONArrayToArray(arrayType, jsonObject.optJSONArray(key));
            } else if (BasePoJo.class.isAssignableFrom(fieldType)) {
                fieldObj = JSONObjectToObject(fieldType, jsonObject.optJSONObject(key));
            } else {
                fieldObj = jsonObject.optString(key);
            }
            if (fieldObj == null) {
                continue;
            }
            try {
                field.set(this, fieldObj);
            } catch (Exception e) {
            }
        }
    }

    //为了防止属性缺失
    private void putObject(JSONObject jsonObject, String key, Object value) {
        try {
            if (value == null) {
                jsonObject.put(key, "");
            } else {
                jsonObject.put(key, value);
            }
        } catch (Exception e) {
        }
    }

    private <T> T JSONObjectToObject(Class fieldType, JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        T result = null;
        try {
            Constructor constructor = fieldType.getConstructor(String.class);
            if (constructor != null) {
                result = (T) constructor.newInstance(jsonObject.toString());
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static <T> ArrayList<T> JSONArrayStrToArray(Class arrayType, String jsonArrayStr) {
        ArrayList<T> tempArrayList = new ArrayList<T>();
        if (jsonArrayStr != null) {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(jsonArrayStr);
            } catch (Exception e) {
            }
            tempArrayList = JSONArrayToArray(arrayType, jsonArray);
        }
        return tempArrayList;
    }

    public static <T> ArrayList<T> JSONArrayToArray(Class arrayType, JSONArray jsonArray) {
        ArrayList<T> tempArrayList = new ArrayList<T>();
        if (jsonArray != null && arrayType != null) {
            Constructor constructor = null;
            try {
                constructor = arrayType.getConstructor(String.class);
            } catch (Exception e) {
            }
            if (constructor != null) {
                for (int j = 0; j < jsonArray.length(); j++) {
                    try {
                        Object obj = jsonArray.get(j);
                        Object tempObj = constructor.newInstance(obj.toString());
                        tempArrayList.add((T) tempObj);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return tempArrayList;
    }

    public static JSONArray arrayToJsonArray(ArrayList arrayList) {
        JSONArray jsonArray = new JSONArray();
        if (arrayList != null) {
            for (Object obj : arrayList) {
                if (obj instanceof BasePoJo) {
                    jsonArray.put(((BasePoJo) obj).toJSONObject());
                } else {
                    jsonArray.put(obj);
                }
            }
        }
        return jsonArray;
    }

    //根据注解将对象转为JSONObject
    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        Class clazz = this.getClass();
        while (BasePoJo.class != clazz) {
            System.out.println("class=" + clazz);
            initJSON(result, clazz);
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    private void initJSON(JSONObject result, Class self) {
        if (self == null) {
            return;
        }
        Field[] fields = self.getDeclaredFields();
        if (fields == null) {
            return;
        }
        for (Field field : fields) {
            FieldDesc fieldDescs = field.getAnnotation(FieldDesc.class);
            if (fieldDescs == null) {
                continue;
            }
            Class fieldType = field.getType();
            String key = fieldDescs.key();
            Object fieldValue = null;
            try {
                fieldValue = field.get(this);
            } catch (Exception e) {
                continue;
            }
            if (ArrayList.class == fieldType) {
                putObject(result, key, arrayToJsonArray((ArrayList) fieldValue));
            } else if (BasePoJo.class.isAssignableFrom(fieldType)) {
                if (fieldValue != null) {
                    putObject(result, key, ((BasePoJo) fieldValue).toJSONObject());
                }
            } else {
                putObject(result, key, fieldValue);
            }
        }
    }

    //方便对象与JSONObject相互转换
    @Override
    public String toString() {
        return toJSONObject().toString();
    }

}