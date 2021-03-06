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
package com.effektif.workflow.impl.conditions;

import com.effektif.workflow.api.condition.Comparator;
import com.effektif.workflow.impl.WorkflowParser;
import com.effektif.workflow.impl.script.ConditionService;
import com.effektif.workflow.impl.workflow.BindingImpl;
import com.effektif.workflow.impl.workflowinstance.ScopeInstanceImpl;


/**
 * @author Tom Baeyens
 */
public abstract class ComparatorImpl extends ConditionImpl<Comparator> {
  
  protected BindingImpl<?> left;
  protected BindingImpl<?> right;

  public BindingImpl<?> getLeft() {
    return this.left;
  }
  public void setLeft(BindingImpl<?> left) {
    this.left = left;
  }
  public ComparatorImpl left(BindingImpl<?> left) {
    this.left = left;
    return this;
  }

  public BindingImpl<?> getRight() {
    return this.right;
  }
  public void setRight(BindingImpl<?> right) {
    this.right = right;
  }
  public ComparatorImpl right(BindingImpl<?> right) {
    this.right = right;
    return this;
  }
  
  @Override
  public boolean eval(ScopeInstanceImpl scopeInstance) {
    Object leftValue = scopeInstance.getValue(left);
    Object rightValue = scopeInstance.getValue(right);
    
    return compare(leftValue, rightValue, scopeInstance);
  }

  public abstract boolean compare(Object leftValue, Object rightValue, ScopeInstanceImpl scopeInstance);
  
  @Override
  public void parse(Comparator comparator, ConditionService conditionService, WorkflowParser parser) {
    this.left = parser.parseBinding(comparator.getLeft(), "left");
    this.right = parser.parseBinding(comparator.getRight(), "right");
  }
}
