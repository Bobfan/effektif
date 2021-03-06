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
package com.effektif.workflow.api.workflowinstance;

import com.effektif.workflow.api.types.Type;


/**
 * @author Tom Baeyens
 */
public class VariableInstance {

  protected String id;
  protected String variableId;
  protected Object value;
  protected Type type;

  public String getId() {
    return this.id;
  }
  public void setId(String id) {
    this.id = id;
  }
  
  public String getVariableId() {
    return this.variableId;
  }
  public void setVariableId(String variableId) {
    this.variableId = variableId;
  }

  public Object getValue() {
    return this.value;
  }
  public void setValue(Object value) {
    this.value = value;
  }
  
  public Type getType() {
    return this.type;
  }
  public void setType(Type type) {
    this.type = type;
  }
}
