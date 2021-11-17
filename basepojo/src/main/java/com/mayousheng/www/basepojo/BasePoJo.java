package com.mayousheng.www.basepojo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
            try {
                field.set(this, getObj(jsonObject, field));
            } catch (Exception e) {
            }
        }
    }

    private Object getObj(JSONObject jsonObject, Field field) {
        FieldDesc fieldDescs = field.getAnnotation(FieldDesc.class);
        if (fieldDescs == null) {
            return null;
        }
        Class fieldType = field.getType();
        String key = fieldDescs.key();
        Class arrayType = fieldDescs.arrayType();
        Class mapType = fieldDescs.mapType();
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
        } else if (HashMap.class == fieldType) {
            JSONObject tempJSONObject = null;
            try {
                tempJSONObject = new JSONObject(jsonObject.optString(key));
            } catch (Exception e) {
            }
            if (tempJSONObject == null) {
                tempJSONObject = jsonObject.optJSONObject(key);
            }
            fieldObj = jsonObject2Map(mapType, tempJSONObject);
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
        return fieldObj;
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

    private HashMap jsonObject2Map(Class mapType, JSONObject jsonObject) {
        HashMap result = new HashMap();
        if (jsonObject == null) {
            return result;
        }
        Iterator<String> keys = jsonObject.keys();
        Object tempObj;
        String key;
        while (keys.hasNext()) {
            key = keys.next();
            try {
                tempObj = jsonObject.get(key);
            } catch (Exception e) {
                continue;
            }
            if (mapType == String.class) {
                result.put(key, tempObj.toString());
            } else if (mapType.isEnum()) {
                try {
                    result.put(key, Enum.valueOf(mapType, tempObj.toString()));
                } catch (Exception e) {
                }
            } else if (mapType == int.class || mapType == Integer.class) {
                try {
                    result.put(key, Integer.parseInt(tempObj.toString()));
                } catch (Exception e) {
                }
            } else if (mapType == long.class || mapType == Long.class) {
                try {
                    result.put(key, Long.parseLong(tempObj.toString()));
                } catch (Exception e) {
                }
            } else if (mapType == boolean.class || mapType == Boolean.class) {
                try {
                    result.put(key, Boolean.parseBoolean(tempObj.toString()));
                } catch (Exception e) {
                }
            } else if (mapType == float.class || mapType == Float.class) {
                try {
                    result.put(key, Float.parseFloat(tempObj.toString()));
                } catch (Exception e) {
                }
            } else if (mapType == double.class || mapType == Double.class) {
                try {
                    result.put(key, Double.parseDouble(tempObj.toString()));
                } catch (Exception e) {
                }
            } else if (BasePoJo.class.isAssignableFrom(mapType)) {
                Object data = null;
                try {
                    Constructor constructor = mapType.getConstructor(String.class);
                    if (constructor != null) {
                        data = constructor.newInstance(tempObj.toString());
                    }
                } catch (Exception e) {
                    data = null;
                }
                try {
                    result.put(key, data);
                } catch (Exception e) {
                }
            } else {
                result.put(key, tempObj.toString());
            }
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

    public static <O extends Object> ArrayList<O> JSONArrayToArray(Class<O> arrayType, JSONArray jsonArray) {
        ArrayList<O> tempArrayList = new ArrayList<>();
        if (jsonArray != null && arrayType != null) {
            if (BasePoJo.class.isAssignableFrom(arrayType)) {
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
                            tempArrayList.add((O) tempObj);
                        } catch (Exception e) {
                        }
                    }
                }
            } else {
                for (int j = 0; j < jsonArray.length(); j++) {
                    try {
                        tempArrayList.add((O) jsonArray.get(j));
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

    public static JSONObject map2JsonObject(HashMap<String, ?> map) {
        JSONObject jsonObject = new JSONObject();
        if (map != null) {
            Object obj;
            for (Map.Entry<String, ?> kv : map.entrySet()) {
                obj = kv.getValue();
                if (obj instanceof BasePoJo) {
                    try {
                        jsonObject.put(kv.getKey(), ((BasePoJo) obj).toJSONObject());
                    } catch (Exception e) {
                    }
                } else {
                    try {
                        jsonObject.put(kv.getKey(), obj);
                    } catch (Exception e) {
                    }
                }
            }
        }
        return jsonObject;
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
            } else if (HashMap.class == fieldType) {
                putObject(result, key, map2JsonObject((HashMap) fieldValue));
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