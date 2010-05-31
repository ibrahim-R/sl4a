/*
 * Copyright (C) 2010 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.ase.future;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * FutureIntent represents an eventual Intent result object for asynchronous operations.
 * 
 * @author Damon Kohler (damonkohler@gmail.com)
 */
public class FutureResult implements Future<Object> {

  private final CountDownLatch mLatch = new CountDownLatch(1);
  private Object mResult;

  public void set(Object result) {
    mResult = result;
    mLatch.countDown();
  }

  @Override
  public boolean cancel(boolean mayInterruptIfRunning) {
    return false;
  }

  @Override
  public Object get() throws InterruptedException {
    mLatch.await();
    return mResult;
  }

  @Override
  public Object get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
    mLatch.await(timeout, unit);
    return mResult;
  }

  @Override
  public boolean isCancelled() {
    return false;
  }

  @Override
  public boolean isDone() {
    return mResult == null;
  }

}