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
package com.effektif.workflow.api.activities;

import com.effektif.workflow.api.types.Type;
import com.effektif.workflow.api.workflow.Activity;
import com.effektif.workflow.api.workflow.MultiInstance;
import com.effektif.workflow.api.workflow.Timer;
import com.effektif.workflow.api.workflow.Transition;
import com.effektif.workflow.api.workflow.Variable;
import com.fasterxml.jackson.annotation.JsonTypeName;


/** 
 * delegates to an activity that is running on an external adapter server 
 * 
 * @author Tom Baeyens
 */
@JsonTypeName("adapterActivity")
public class AdapterActivity extends AbstractBindableActivity {

  protected String adapterId;
  protected String activityKey;

  @Override
  public AdapterActivity id(String id) {
    super.id(id);
    return this;
  }

  public String getAdapterId() {
    return this.adapterId;
  }
  public void setAdapterId(String adapterId) {
    this.adapterId = adapterId;
  }
  public AdapterActivity adapterId(String adapterId) {
    this.adapterId = adapterId;
    return this;
  }
  
  public String getActivityKey() {
    return this.activityKey;
  }
  public void setActivityKey(String activityKey) {
    this.activityKey = activityKey;
  }
  public AdapterActivity activityKey(String activityKey) {
    this.activityKey = activityKey;
    return this;
  }
  
  @Override
  public AdapterActivity inputValue(String adapterKey, Object value) {
    super.inputValue(adapterKey, value);
    return this;
  }

  @Override
  public AdapterActivity outputBinding(String adapterKey, String variableId) {
    super.outputBinding(adapterKey, variableId);
    return this;
  }

  /**
   * @see <a href="https://github.com/effektif/effektif/wiki/Multi-instance-tasks">Multi-instance tasks</a>
   */
  @Override
  public AdapterActivity multiInstance(MultiInstance multiInstance) {
    super.multiInstance(multiInstance);
    return this;
  }

  @Override
  public AdapterActivity transitionTo(String toActivityId) {
    super.transitionTo(toActivityId);
    return this;
  }

  @Override
  public AdapterActivity transitionTo(Transition transition) {
    super.transitionTo(transition);
    return this;
  }

  @Override
  public AdapterActivity activity(Activity activity) {
    super.activity(activity);
    return this;
  }

  @Override
  public AdapterActivity transition(Transition transition) {
    super.transition(transition);
    return this;
  }

  @Override
  public AdapterActivity variable(Variable variable) {
    super.variable(variable);
    return this;
  }

  @Override
  public AdapterActivity timer(Timer timer) {
    super.timer(timer);
    return this;
  }

  @Override
  public AdapterActivity property(String key, Object value) {
    super.property(key, value);
    return this;
  }
  @Override
  public AdapterActivity inputExpression(String key, String expression) {
    super.inputExpression(key, expression);
    return this;
  }
  @Override
  public AdapterActivity transitionToNext() {
    super.transitionToNext();
    return this;
  }
  @Override
  public AdapterActivity activity(String id, Activity activity) {
    super.activity(id, activity);
    return this;
  }
  @Override
  public AdapterActivity transition(String id, Transition transition) {
    super.transition(id, transition);
    return this;
  }
  @Override
  public AdapterActivity variable(String id, Type type) {
    super.variable(id, type);
    return this;
  }
  @Override
  public AdapterActivity name(String name) {
    super.name(name);
    return this;
  }
}
