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
package com.effektif.workflow.impl.identity;

import java.util.List;

import com.effektif.workflow.api.model.GroupId;
import com.effektif.workflow.api.model.UserId;


/**
 * @author Tom Baeyens
 */
public interface IdentityService {

  User createUser(User user);
  List<String> getUsersEmailAddresses(List<UserId> userIds);
  User findUserById(UserId userId);

  Group createGroup(Group group);
  List<String> getGroupsEmailAddresses(List<GroupId> groupIds);
  Group findGroupById(GroupId groupId);
  List<Group> findGroupByIds(List<GroupId> groupIds);
}
