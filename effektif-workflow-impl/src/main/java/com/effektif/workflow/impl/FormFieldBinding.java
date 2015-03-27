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
package com.effektif.workflow.impl;

import com.effektif.workflow.api.form.FormField;
import com.effektif.workflow.api.form.FormInstanceField;
import com.effektif.workflow.impl.workflow.BindingImpl;


/**
 * @author Tom Baeyens
 */
public class FormFieldBinding {

  public FormField formField;
  public BindingImpl binding;

  public FormFieldBinding(FormField formField, BindingImpl binding) {
    this.formField = formField;
    this.binding = binding;
  }

  public void deserializeFormField(FormInstanceField field) {
    Object value = field!=null ? field.getValue() : null;
    if (value!=null 
        && binding!=null 
        && binding.expression!=null 
        && binding.expression.type!=null) {
      Object deserializedValue = binding.expression.type.convertJsonToInternalValue(value);
      field.setValue(deserializedValue);
    }
  }
}
