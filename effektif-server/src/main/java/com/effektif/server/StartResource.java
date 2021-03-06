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
package com.effektif.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.effektif.workflow.api.model.TriggerInstance;
import com.effektif.workflow.api.workflowinstance.WorkflowInstance;
import com.effektif.workflow.impl.WorkflowEngineImpl;


/**
 * @author Tom Baeyens
 */
@Path("/start")
public class StartResource {
  
  public static final Logger log = LoggerFactory.getLogger(StartResource.class);
  
  WorkflowEngineImpl workflowEngine;
  
  public StartResource(WorkflowEngineImpl workflowEngine) {
    this.workflowEngine = workflowEngine;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public WorkflowInstance startProcessInstance(TriggerInstance triggerInstance) {
    WorkflowInstance workflowInstance = workflowEngine.start(triggerInstance, true);
    log.info("Started "+workflowInstance.getId());
    return workflowInstance;
  }
}
