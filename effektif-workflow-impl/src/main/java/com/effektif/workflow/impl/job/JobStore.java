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
package com.effektif.workflow.impl.job;

import java.util.List;


/**
 * @author Tom Baeyens
 */
public interface JobStore {

  void saveJob(Job job);
  List<Job> findJobs(JobQuery jobQuery);
  void deleteJobById(String jobId);
  void deleteJobs(JobQuery jobQuery);

  void saveArchivedJob(Job job);
  List<Job> findArchivedJobs(JobQuery jobQuery);
  void deleteArchivedJobs(JobQuery jobQuery);

  /** locks a job not having a {@link Job#lock} specified
   * and retrieves it from the store */
  Job lockNextJob();

}
