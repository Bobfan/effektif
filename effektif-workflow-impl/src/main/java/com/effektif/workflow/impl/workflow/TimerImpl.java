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
package com.effektif.workflow.impl.workflow;

import java.util.Map;

import com.effektif.workflow.api.Configuration;
import com.effektif.workflow.api.workflow.Timer;
import com.effektif.workflow.impl.WorkflowParser;
import com.effektif.workflow.impl.job.JobType;


public class TimerImpl {
  
  public String id;
  public ScopeImpl parent;
  public Configuration configuration;
  public WorkflowImpl workflow;
  public JobType jobType;
  public Map<String,Object> properties;

  public void parse(Timer timerApi, ScopeImpl parent, WorkflowParser parser) {
    this.id = timerApi.getId();
    this.configuration = parser.configuration;
    this.properties = timerApi.getProperties();
    if (parent!=null) {
      this.parent = parent;
      this.workflow = parent.workflow;
    }
  }

  public Timer serialize() {
    Timer timer = new Timer();
    timer.setId(id);
    timer.setProperties(properties);
    // TODO jobType
    return timer;
  }
}
