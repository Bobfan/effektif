/*
 * Copyright 2014 Effektif GmbH.
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
 * limitations under the License.
 */
package com.effektif.workflow.impl.data;

import java.util.Map;
import java.util.Map.Entry;

import com.effektif.workflow.api.Configuration;
import com.effektif.workflow.api.types.Type;
import com.effektif.workflow.api.workflow.Binding;
import com.effektif.workflow.api.xml.XmlElement;
import com.effektif.workflow.impl.template.Hints;


public abstract class AbstractDataType<T extends Type> implements DataType<T> {
  
  protected T type;
  protected Class<? extends Type> apiClass;
  protected Class<?> valueClass;
  protected Configuration configuration;

  public AbstractDataType(T typeApi, Class< ? > valueClass) {
    this.type = typeApi;
    this.apiClass = typeApi!=null ? typeApi.getClass() : null;
    this.valueClass = valueClass;
  }
  
  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }
  
  protected <D extends DataType> D getSingletonDataType(Class<D> dataTypeClass) {
    DataTypeService dataTypeService = this.configuration.get(DataTypeService.class);
    for (DataType dataType: dataTypeService.singletons.values()) {
      if (dataTypeClass.equals(dataType.getClass())) {
        return (D) dataType;
      }
    }
    for (Entry<Class< ? extends Type>, DataType> entry: dataTypeService.singletons.entrySet()) {
      System.err.println(entry.getKey().toString()+" -> "+entry.getValue().toString());
    }
    throw new RuntimeException("Couldn't find singleton "+dataTypeClass.getName());
  }
  
  @Override
  public boolean isStatic() {
    return true;
  }
  
  public Class<? extends Type> getApiClass() {
    return apiClass;
  }

  @Override
  public Class< ? > getValueClass() {
    return valueClass;
  }
  
  @Override
  public Object convertJsonToInternalValue(Object jsonValue) throws InvalidValueException {
    return jsonValue;
  }

  public Object convertInternalToJsonValue(Object internalValue) {
    return internalValue;
  }

  public void validateInternalValue(Object internalValue) throws InvalidValueException {
  }

  public TypedValueImpl dereference(Object value, String field) {
    return null;
  }

  @Override
  public TypeGenerator getTypeGenerator() {
    return null;
  }
  
  @Override
  public T serialize() {
    return type;
  }

  @Override
  public String convertInternalToText(Object value, Hints hints) {
    return value!=null ? value.toString() : null;
  }

  @Override
  public Binding readValue(XmlElement xml) { return null; }

  /**
   * Returns a <code>String</code> binding with the value read from the attribute with the given name.
   */
  protected String readStringValue(XmlElement xml, String attributeName) {
    if (xml == null) {
      throw new IllegalArgumentException("null argument to method");
    }
    return xml.attributes.get(attributeName);
  }

  @Override
  public void writeValue(XmlElement xml, Object value) { }

  
  public T getType() {
    return type;
  }
}
