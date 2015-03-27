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
package com.effektif.workflow.api.workflow;

import java.util.ArrayList;
import java.util.List;

import com.effektif.workflow.api.types.Type;


/**
 * @author Tom Baeyens
 */
public class Scope extends Element {

  protected List<Activity> activities;
  protected List<Transition> transitions;
  protected List<Variable> variables;
  protected List<Timer> timers;

  public List<Activity> getActivities() {
    return this.activities;
  }
  public void setActivities(List<Activity> activities) {
    this.activities = activities;
  }
  public Scope activity(String id, Activity activity) {
    activity.setId(id);
    activity(activity);
    return this;
  }
  public Scope activity(Activity activity) {
    if (this.activities==null) {
      this.activities = new ArrayList<>();
    }
    this.activities.add(activity);
    return this;
  }
  
  public List<Transition> getTransitions() {
    return this.transitions;
  }
  public void setTransitions(List<Transition> transitions) {
    this.transitions = transitions;
  }
  public Scope transition(String id, Transition transition) {
    transition.setId(id);
    transition(transition);
    return this;
  }
  public Scope transition(Transition transition) {
    if (this.transitions==null) {
      this.transitions = new ArrayList<>();
    }
    this.transitions.add(transition);
    return this;
  }
  
  public List<Variable> getVariables() {
    return this.variables;
  }
  public void setVariables(List<Variable> variables) {
    this.variables = variables;
  }
  public Scope variable(String id, Type type) {
    Variable variable = new Variable();
    variable.setId(id);
    variable.setType(type);
    variable(variable);
    return this;
  }
  public Scope variable(Variable variable) {
    if (this.variables==null) {
      this.variables = new ArrayList<>();
    }
    this.variables.add(variable);
    return this;
  }

  
  public List<Timer> getTimers() {
    return this.timers;
  }
  public void setTimers(List<Timer> timers) {
    this.timers = timers;
  }
  public Scope timer(Timer timer) {
    if (this.timers==null) {
      this.timers = new ArrayList<>();
    }
    this.timers.add(timer);
    return this;
  }
}
