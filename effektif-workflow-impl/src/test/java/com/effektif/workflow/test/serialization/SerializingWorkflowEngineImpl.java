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
package com.effektif.workflow.test.serialization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.effektif.workflow.api.WorkflowEngine;
import com.effektif.workflow.api.model.Deployment;
import com.effektif.workflow.api.model.Message;
import com.effektif.workflow.api.model.TriggerInstance;
import com.effektif.workflow.api.model.WorkflowInstanceId;
import com.effektif.workflow.api.query.WorkflowInstanceQuery;
import com.effektif.workflow.api.query.WorkflowQuery;
import com.effektif.workflow.api.workflow.ParseIssues;
import com.effektif.workflow.api.workflow.Workflow;
import com.effektif.workflow.api.workflowinstance.WorkflowInstance;
import com.effektif.workflow.impl.WorkflowEngineImpl;
import com.effektif.workflow.impl.json.JsonService;
import com.effektif.workflow.impl.json.SerializedMessage;
import com.effektif.workflow.impl.json.SerializedTriggerInstance;
import com.effektif.workflow.impl.json.SerializedWorkflow;


/**
 * @author Tom Baeyens
 */
public class SerializingWorkflowEngineImpl extends AbstractSerializingService implements WorkflowEngine {
  
  WorkflowEngineImpl workflowEngine;

  public SerializingWorkflowEngineImpl(WorkflowEngineImpl workflowEngine, JsonService jsonService) {
    super(jsonService);
    this.workflowEngine = workflowEngine;
  }

  @Override
  public Deployment deployWorkflow(Workflow workflow) {
    log.debug("deployWorkflow");
    workflow = wireize(" >>workflow>> ", workflow, Workflow.class);
    ParseIssues parseIssues = workflowEngine.deployWorkflow(workflow, true);
    return wireize("  <<deployment<< ", parseIssues, Deployment.class);
  }

  @Override
  public List<Workflow> findWorkflows(WorkflowQuery query) {
    log.debug("findWorkflow");
    query = wireize(" >>query>> ", query, WorkflowQuery.class);
    List<Workflow> workflows = workflowEngine.findWorkflows(query);
    if (workflows==null) {
      return null;
    }
    List<Workflow> wirizedWorkflows = new ArrayList<>(workflows.size());
    for (Workflow workflow: workflows) {
      wirizedWorkflows.add(wireize("  <<workflow<< ", workflow, Workflow.class));
    }
    return wirizedWorkflows;
  }

  @Override
  public void deleteWorkflows(WorkflowQuery query) {
    log.debug("deleteWorkflow");
    query = wireize(" >>query>> ", query, WorkflowQuery.class);
    workflowEngine.deleteWorkflows(query);
  }

  @Override
  public WorkflowInstance start(TriggerInstance triggerInstance) {
    log.debug("startWorkflow");
    triggerInstance = wireize(" >>start>> ", triggerInstance, TriggerInstance.class);
    WorkflowInstance workflowInstance = workflowEngine.start(triggerInstance, true);
    workflowInstance = wireize("  <<workflowInstance<< ", workflowInstance, WorkflowInstance.class);
    workflowEngine.deserializeWorkflowInstance(workflowInstance);
    return workflowInstance;
  }

  @Override
  public WorkflowInstance send(Message message) {
    log.debug("sendMessage");
    message = wireize(" >>message>> ", message, Message.class);
    WorkflowInstance workflowInstance = workflowEngine.send(message, true);
    workflowInstance = wireize("  <<workflowInstance<< ", workflowInstance, WorkflowInstance.class);
    workflowEngine.deserializeWorkflowInstance(workflowInstance);
    return workflowInstance;
  }
  
  @Override
  public Map<String, Object> getVariableValues(WorkflowInstanceId workflowInstanceId) {
    log.debug("getVariableValues");
    Map<String, Object> variableValues = workflowEngine.getVariableValues(workflowInstanceId);
    variableValues = wireize("  <<variableValues<< ", variableValues, Map.class);
    workflowEngine.deserializeVariableValues(workflowInstanceId, variableValues);
    return variableValues;
  }

  @Override
  public Map<String, Object> getVariableValues(WorkflowInstanceId workflowInstanceId, String activityInstanceId) {
    log.debug("getVariableValues");
    Map<String, Object> variableValues = workflowEngine.getVariableValues(workflowInstanceId, activityInstanceId);
    variableValues = wireize("  <<variableValues<< ", variableValues, Map.class);
    workflowEngine.deserializeVariableValues(workflowInstanceId, variableValues);
    return variableValues;
  }

  @Override
  public void setVariableValues(WorkflowInstanceId workflowInstanceId, Map<String, Object> variableValues) {
    log.debug("setVariableValues");
    variableValues = wireize(" >>variableValues>> ", variableValues, Map.class);
    workflowEngine.setVariableValues(workflowInstanceId, null, variableValues, true);
  }

  @Override
  public void setVariableValues(WorkflowInstanceId workflowInstanceId, String activityInstanceId, Map<String, Object> variableValues) {
    log.debug("setVariableValues");
    variableValues = wireize(" >>variableValues>> ", variableValues, Map.class);
    workflowEngine.setVariableValues(workflowInstanceId, activityInstanceId, variableValues, true);
  }

  @Override
  public List<WorkflowInstance> findWorkflowInstances(WorkflowInstanceQuery query) {
    log.debug("findWorkflowInstances");
    query = wireize(" >>query>>", query, WorkflowInstanceQuery.class);
    List<WorkflowInstance> workflowInstances = workflowEngine.findWorkflowInstances(query);
    if (workflowInstances==null) {
      return null;
    }
    List<WorkflowInstance> wirizedWorkflowInstances = new ArrayList<>(workflowInstances.size());
    for (WorkflowInstance workflowInstance: workflowInstances) {
      workflowInstance = wireize("  <-workflowInstance-", workflowInstance, WorkflowInstance.class);
      workflowEngine.deserializeWorkflowInstance(workflowInstance);
      wirizedWorkflowInstances.add(workflowInstance);
    }
    return wirizedWorkflowInstances;
  }

  @Override
  public void deleteWorkflowInstances(WorkflowInstanceQuery query) {
    log.debug("deleteWorkflowInstances");
    query = wireize(" >>query>>", query, WorkflowInstanceQuery.class);
    workflowEngine.deleteWorkflowInstances(query);
  }
}
