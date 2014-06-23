/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.demo.test;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Daniel Meyer
 *
 */
public class MessageCorrelationTest {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources="shipmentProcess.bpmn")
  public void shouldNotCorrelateMessageByExecutionId() {

    Map<String, Object> processVariables = new HashMap<String, Object>();
    processVariables.put("orderId", "mockOrderId");

    RuntimeService runtimeService = processEngineRule.getRuntimeService();

    ProcessInstance pi = runtimeService.startProcessInstanceByKey("shipmentProcess", processVariables);
    assertNotNull(runtimeService
        .createProcessInstanceQuery()
        .processDefinitionKey("shipmentProcess")
        .singleResult());


    String executionId = SendMessageMock.waitingExecutions.get(0);

    try {
      // Anti pattern! do not do this
      runtimeService.signal(executionId);
      fail("exception expected");
    } catch (Exception e) {
      // expected
    }
  }

  @Test
  @Deployment(resources="shipmentProcess.bpmn")
  public void shouldCorrelateMessageByMessageName() {

    Map<String, Object> processVariables = new HashMap<String, Object>();
    processVariables.put("orderId", "mockOrderId");

    RuntimeService runtimeService = processEngineRule.getRuntimeService();

    ProcessInstance pi = runtimeService.startProcessInstanceByKey("shipmentProcess", processVariables);
    assertNotNull(runtimeService
        .createProcessInstanceQuery()
        .processDefinitionKey("shipmentProcess")
        .singleResult());

    runtimeService.createMessageCorrelation("shipmentPerformed")
      .processInstanceVariableEquals("orderId", "mockOrderId")
      .correlate();

    assertNull(runtimeService
        .createProcessInstanceQuery()
        .processDefinitionKey("shipmentProcess")
        .singleResult());
  }

}
