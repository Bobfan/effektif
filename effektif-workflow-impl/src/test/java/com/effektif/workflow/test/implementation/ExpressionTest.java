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
package com.effektif.workflow.test.implementation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.effektif.workflow.api.activities.UserTask;
import com.effektif.workflow.api.model.TriggerInstance;
import com.effektif.workflow.api.model.UserId;
import com.effektif.workflow.api.task.Task;
import com.effektif.workflow.api.types.ListType;
import com.effektif.workflow.api.types.Type;
import com.effektif.workflow.api.types.UserIdType;
import com.effektif.workflow.api.workflow.Workflow;
import com.effektif.workflow.impl.WorkflowEngineImpl;
import com.effektif.workflow.impl.WorkflowParser;
import com.effektif.workflow.impl.identity.IdentityService;
import com.effektif.workflow.impl.identity.User;
import com.effektif.workflow.impl.util.Lists;
import com.effektif.workflow.impl.workflow.ExpressionImpl;
import com.effektif.workflow.impl.workflowinstance.WorkflowInstanceImpl;
import com.effektif.workflow.test.WorkflowTest;


/**
 * @author Tom Baeyens
 */
public class ExpressionTest extends WorkflowTest {

  @Test
  public void testExpressionUserDereferencing() {
    User johndoe = new User()
      .id("johndoe")
      .fullName("John Doe")
      .email("johndoe@localhost");
    
    configuration.get(IdentityService.class)
      .createUser(johndoe);
    
    assertEquals("johndoe@localhost", 
            evaluate(new UserIdType(), "v", new UserId("johndoe"), "v.email"));
    
    assertEquals("John Doe", 
            evaluate(new UserIdType(), "v", new UserId("johndoe"), "v.fullName"));

    assertEquals("johndoe", 
            evaluate(new UserIdType(), "v", new UserId("johndoe"), "v.id"));

    assertEquals(new UserId("johndoe"), 
            evaluate(new UserIdType(), "v", new UserId("johndoe"), "v"));

    User user = (User) evaluate(new UserIdType(), "v", new UserId("johndoe"), "v.*");
    assertEquals(new UserId("johndoe"),user.getId());
    assertEquals("John Doe",user.getFullName());
    assertEquals("johndoe@localhost",user.getEmail());
  }

  @Test
  public void testBindingExpressionListFlattening() {
    User johndoe = new User()
      .id("johndoe")
      .fullName("John Doe")
      .email("johndoe@localhost");
  
    User joesmoe = new User()
      .id("joesmoe")
      .fullName("Joe Smoe")
      .email("joesmoe@localhost");
  
    User jackblack = new User()
      .id("jackblack")
      .fullName("Jack Black")
      .email("jackblack@localhost");
  
    IdentityService identityService = configuration.get(IdentityService.class);
    identityService.createUser(johndoe);
    identityService.createUser(joesmoe);
    identityService.createUser(jackblack);
    
    Workflow workflow = new Workflow()
      .variable("manager", new UserIdType())
      .variable("workers", new ListType(new UserIdType()))
      .activity("1", new UserTask()
        .candidateExpression("manager")
        .candidateExpression("workers"));
    
    deploy(workflow);
    
    workflowEngine.start(new TriggerInstance()
      .data("manager", new UserId("johndoe"))
      .data("workers", Lists.of(new UserId("joesmoe"), new UserId("jackblack")))
      .workflowId(workflow.getId()));
    
    Task task = taskService.findTasks(null).get(0);
    assertEquals(Lists.of(new UserId("johndoe"), new UserId("joesmoe"), new UserId("jackblack")), task.getCandidateIds());
  }
  
  public Object evaluate(Type type, String variableId, Object value, String expression) {
    Workflow workflow = new Workflow()
      .variable(variableId, type);
    
    deploy(workflow);
    
    TriggerInstance triggerInstance = new TriggerInstance()
      .data(variableId, value)
      .workflowId(workflow.getId());
    
    WorkflowEngineImpl workflowEngineImpl = (WorkflowEngineImpl) workflowEngine;
    WorkflowInstanceImpl workflowInstance = workflowEngineImpl.startInitialize(triggerInstance);
  
    ExpressionImpl expressionImpl = new ExpressionImpl();
    expressionImpl.parse(expression, new WorkflowParser(configuration));
    
    return workflowInstance.getValue(expressionImpl);
  }
}
