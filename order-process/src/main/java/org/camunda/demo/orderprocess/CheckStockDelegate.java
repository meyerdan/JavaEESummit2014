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

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * @author Daniel Meyer
 *
 */
@Named
public class CheckStockDelegate implements JavaDelegate {

  @PersistenceContext
  private EntityManager entityManager;

  @Inject
  private OrderProcessDataAccessor dataAccessor;

  public void execute(DelegateExecution execution) throws Exception {

    OrderEntity order = new OrderEntity();
    order.setCustomerId(dataAccessor.getCustomerId());

    String amount = (String) execution.getVariable("orderAmount");
    order.setOrderAmount(Double.parseDouble(amount));

    // check inventory
    order.setOutOfStock(true);

    entityManager.persist(order);
    entityManager.flush();

    Long orderId = order.getId();

    execution.setVariable("orderId", orderId);

    dataAccessor.setOrderId(orderId);

  }

}
