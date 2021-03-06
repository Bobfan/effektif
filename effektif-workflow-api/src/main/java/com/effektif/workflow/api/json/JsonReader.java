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
package com.effektif.workflow.api.json;

import java.util.List;
import java.util.Map;

import com.effektif.workflow.api.model.Id;


/**
 * @author Tom Baeyens
 */
public interface JsonReader {

  <T extends Id> T readId(Class<T> idType);

  <T extends Id> T readId(String fieldName, Class<T> idType);

  <T extends JsonReadable> List<T> readList(String fieldName, Class<T> type);

  <T extends JsonReadable> T readObject(String fieldName, Class<T> type);

  <T> Map<String, T> readMap(String fieldName, Class<T> valueType);

  String readString(String fieldName);

}
