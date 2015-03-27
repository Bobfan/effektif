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
package com.effektif.workflow.api.acl;


/** Stores the current authentication information per thread.
 * 
 * @author Tom Baeyens
 */
public class Authentications {

  protected static ThreadLocal<Authentication> current = new ThreadLocal<>();

  public static void set(Authentication authentication) {
    current.set(authentication);
  }

  public static void unset() {
    current.remove();
  }
  
  public static Authentication current() {
    return current.get();
  }

  public static Authentication authenticate(String authenticatedUserId, String organizationId) {
    AuthenticationImpl authorization = new AuthenticationImpl()
      .actorId(authenticatedUserId)
      .organizationId(organizationId);
    set(authorization);
    return authorization;
  }
}
