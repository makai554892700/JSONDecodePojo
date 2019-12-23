package com.mayousheng.www.basepojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by marking on 2017/3/24.
 */

public abstract class BasePoJo {

    public static <T extends BasePoJo> T fromJsonStr(
            String jsonStr, T data) {
        if (jsonStr == null || jsonStr.isEmpty() || data == null) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStr);
        } catch (Exception e) {
        }
        if (jsonObject == null) {
            return null;
        }
        Class clazz = data.getClass();
        while (BasePoJo.class != clazz) {
            data.initField(jsonObject, clazz);
            clazz = clazz.getSuperclass();
        }
        return data;
    }

    public BasePoJo(String jsonStr) {
        fromJsonStr(jsonStr, this);
    }

    public void initField(JSONObject jsonObject, Class clazz) {
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
            }
            Class fieldType = field.getType();
            String key = fieldDescs.key();
            Class arrayType = fieldDescs.arrayType();
            Object fieldObj = null;
            if (fieldType == String.class) {
                fieldObj = jsonObject.optString(key);
            } else if (fieldType.isEnum()) {
                try {
                    fieldObj = Enum.valueOf(fieldType, jsonObject.optString(key));
                } catch (Exception e) {
                }
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
                JSONArray tempJSONArray = null;
                try {
                    tempJSONArray = new JSONArray(jsonObject.optString(key));
                } catch (Exception e) {
                }
                if (tempJSONArray == null) {
                    tempJSONArray = jsonObject.optJSONArray(key);
                }
                fieldObj = JSONArrayToArray(arrayType, tempJSONArray);
            } else if (BasePoJo.class.isAssignableFrom(fieldType)) {
                JSONObject tempJSONObject = null;
                try {
                    tempJSONObject = new JSONObject(jsonObject.optString(key));
                } catch (Exception e) {
                }
                if (tempJSONObject == null) {
                    tempJSONObject = jsonObject.optJSONObject(key);
                }
                fieldObj = jsonObject2Object(fieldType, tempJSONObject);
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

    public static <T extends BasePoJo> T strToObject(Class<T> fieldType, String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (Exception e) {
        }
        return JSONObjectToObject(fieldType, jsonObject);
    }

    public static <T extends BasePoJo> T JSONObjectToObject(Class<T> fieldType, JSONObject jsonObject) {
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

    private <T extends BasePoJo> Object jsonObject2Object(Class<T> fieldType
            , JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        }
        Object result = null;
        try {
            Constructor constructor = fieldType.getConstructor(String.class);
            if (constructor != null) {
                result = constructor.newInstance(jsonObject.toString());
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static <T extends BasePoJo> ArrayList<T> JSONArrayStrToArray(Class<T> arrayType, String jsonArrayStr) {
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

    public static <T extends BasePoJo> ArrayList<T> JSONArrayToArray(Class<T> arrayType, JSONArray jsonArray) {
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

    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        Class clazz = this.getClass();
        while (BasePoJo.class != clazz) {
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
            if (result.has(key)) {
                continue;
            }
            Object fieldValue;
            try {
                fieldValue = field.get(this);
            } catch (Exception e) {
                continue;
            }
            if (fieldValue != null) {
                fieldType = fieldValue.getClass();
            }
            if (ArrayList.class == fieldType) {
                putObject(result, key, arrayToJsonArray((ArrayList) fieldValue));
            } else if (BasePoJo.class.isAssignableFrom(fieldType) && fieldValue != null) {
                putObject(result, key, ((BasePoJo) fieldValue).toJSONObject());
            } else {
                putObject(result, key, fieldValue);
            }
        }
    }

    @Override
    public String toString() {
        return toJSONObject().toString();
    }

}