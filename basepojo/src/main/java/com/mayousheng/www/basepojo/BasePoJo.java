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
        Class self = this.getClass();
        Field[] fields = self.getDeclaredFields();
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
            if (fieldType == String.class) {
                //根据属性不同类型调用不同方法解析，同时不影响继续解析
                //目前可能存在考虑不周的地方可根据自己的需求再另行更改
                try {
                    field.set(this, jsonObject.optString(key));
                } catch (Exception e) {
                }
            } else if (fieldType == int.class) {
                try {
                    field.setInt(this, jsonObject.optInt(key));
                } catch (Exception e) {
                }
            } else if (fieldType == long.class) {
                try {
                    field.setLong(this, jsonObject.optLong(key));
                } catch (Exception e) {
                }
            } else if (fieldType == boolean.class) {
                try {
                    field.setBoolean(this, jsonObject.optBoolean(key));
                } catch (Exception e) {
                }
            } else if (fieldType == double.class) {
                try {
                    field.setDouble(this, jsonObject.optDouble(key));
                } catch (Exception e) {
                }
            } else if (ArrayList.class == fieldType) {
                JSONArray jsonArray = jsonObject.optJSONArray(key);
                if (jsonArray == null) {
                    continue;
                }
                ArrayList tempArrayList = null;
                try {
                    tempArrayList = JSONArrayToArray(arrayType, jsonArray);
                } catch (Exception e) {
                }
                try {
                    field.set(this, tempArrayList);
                } catch (Exception e) {
                }
            } else if (BasePoJo.class.isAssignableFrom(fieldType)) {
                JSONObject tempJSONObject = jsonObject.optJSONObject(key);
                if (tempJSONObject == null) {
                    continue;
                }
                BasePoJo object = JSONObjectToObject(fieldType, tempJSONObject);
                if (object != null) {
                    try {
                        field.set(this, object);
                    } catch (Exception e) {
                    }
                }
            } else {
                try {
                    field.set(this, jsonObject.optString(key));
                } catch (Exception e) {
                }
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
                        tempArrayList.add((T) constructor.newInstance(obj.toString()));
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
        Class self = this.getClass();
        Field[] fields = self.getDeclaredFields();
        if (fields == null) {
            return result;
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
            }
            if (ArrayList.class == fieldType) {
                putObject(result, key, arrayToJsonArray((ArrayList) fieldValue));
            } else if (self.isAssignableFrom(fieldType)) {
                putObject(result, key, fieldValue);
            } else {
                putObject(result, key, fieldValue);
            }
        }
        return result;
    }

    //方便对象与JSONObject相互转换
    @Override
    public String toString() {
        return toJSONObject().toString();
    }

}