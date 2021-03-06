/* Copyright (c) 2014, Effektif GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */
package com.effektif.workflow.impl.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.effektif.workflow.api.json.JsonReadable;
import com.effektif.workflow.api.json.JsonReader;
import com.effektif.workflow.api.model.Id;
import com.effektif.workflow.impl.json.deprecated.JsonMappings;


/**
 * @author Tom Baeyens
 */
public abstract class AbstractJsonReader implements JsonReader {
  
  public static DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.dateTimeParser();

  protected Map<String,Object> jsonObject;
  protected JsonMappings jsonMappings; 

  public AbstractJsonReader() {
    this(new JsonMappings());
  }

  public AbstractJsonReader(JsonMappings jsonMappings) {
    this.jsonMappings = jsonMappings;
  }

  protected <T extends JsonReadable> T readCurrentObject(Class<T> type) {
    try {
      Class<T> concreteType = jsonMappings.getConcreteClass(jsonObject, type);
      T o = concreteType.newInstance();
      o.readFields(this);
      return o;
      
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  private static final Class< ? >[] ID_CONSTRUCTOR_PARAMETERS = new Class< ? >[] { String.class };
  @Override
  public <T extends Id> T readId(String fieldName, Class<T> idType) {
    Object id = jsonObject.get(fieldName);
    if (id!=null) {
      try {
        id = id.toString();
        Constructor<T> c = idType.getDeclaredConstructor(ID_CONSTRUCTOR_PARAMETERS);
        return c.newInstance(new Object[] { id });
      } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
              | InvocationTargetException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  public String readString(String fieldName) {
    return (String) jsonObject.get(fieldName);
  }

  @Override
  public <T extends JsonReadable> List<T> readList(String fieldName, Class<T> type) {
    List<Map<String,Object>> jsons = (List<Map<String, Object>>) jsonObject.get(fieldName);
    if (jsons==null) {
      return null;
    }
    Map<String,Object> parentJson = jsonObject;
    List<T> objects = new ArrayList<>();
    for (Map<String,Object> jsonElement: jsons) {
      jsonObject = jsonElement;
      T object = readCurrentObject(type);
      objects.add(object);
    }
    this.jsonObject = parentJson;
    return objects;
  }
  
  @Override
  public <T extends JsonReadable> T readObject(String fieldName, Class<T> type) {
    Map<String,Object> parentJson = jsonObject;
    List<T> objects = new ArrayList<>();
    jsonObject = (Map<String, Object>) parentJson.get(fieldName);
    T object = readCurrentObject(type);
    objects.add(object);
    this.jsonObject = parentJson;
    return object;
  }
  
  @Override
  public <T> Map<String, T> readMap(String fieldName, Class<T> valueType) {
    Map<String,Object> jsons = (Map<String,Object>) jsonObject.get(fieldName);
    if (jsons==null) {
      return null;
    }
    Map<String,Object> parentJson = jsonObject;
    Map<String,T> map = new HashMap<>();
    for (String key: jsons.keySet()) {
      Object jsonElement = jsons.get(key);
      T value = readAny(jsonElement, valueType);
      map.put(key, value);
    }
    this.jsonObject = parentJson;
    return map;
  }

  protected <T> T readAny(Object json, Class<T> type) {
    if (json==null
        || type==String.class
        || type==Boolean.class
        || Number.class.isAssignableFrom(type)) {
      return (T) json;
    }
    if (JsonReadable.class.isAssignableFrom(type)) {
      Map<String,Object> parentJson = jsonObject;
      jsonObject = (Map<String, Object>) json;
      T object = (T) readCurrentObject((Class<JsonReadable>)type);
      jsonObject = parentJson;
      return object;
    }
    if (type==LocalDateTime.class) {
      return (T) DATE_FORMAT.parseLocalDateTime((String)json);
    }
    throw new RuntimeException("Couldn't parse "+json+" ("+json.getClass().getName()+")");
  }

}
