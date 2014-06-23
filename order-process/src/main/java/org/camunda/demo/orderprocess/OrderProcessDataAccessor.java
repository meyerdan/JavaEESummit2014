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
package org.camunda.demo.orderprocess;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.camunda.bpm.engine.cdi.BusinessProcess;

/**
 * @author Daniel Meyer
 *
 */
@ApplicationScoped
public class OrderProcessDataAccessor {

  public static final String VAR_NAME_ORDER_AMOUNT = "orderAmount";

  public static final String VAR_NAME_CUSTOMER_ID = "customerId";

  public static final String VAR_NAME_ORDER_ID = "orderId";

  @Inject
  private BusinessProcess businessProcess;

  public String getCustomerId() {
    return businessProcess.getVariable(VAR_NAME_CUSTOMER_ID);
  }

  public void setCustomerId(String customerId) {
    businessProcess.setVariable(VAR_NAME_CUSTOMER_ID, customerId);
  }

  public Double getOrderAmount() {
    return businessProcess.getVariable(VAR_NAME_ORDER_AMOUNT);
  }

  public void setOrderAmount(Double orderAmount) {
    businessProcess.setVariable(VAR_NAME_ORDER_AMOUNT, orderAmount);
  }

  public Long getOrderId() {
    return businessProcess.getVariable(VAR_NAME_ORDER_ID);
  }

  public void setOrderId(Long orderId) {
    businessProcess.setVariable(VAR_NAME_ORDER_ID, orderId);
  }

}
