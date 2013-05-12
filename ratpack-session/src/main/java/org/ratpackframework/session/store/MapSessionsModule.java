/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ratpackframework.session.store;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import org.ratpackframework.guice.HandlerDecoratingModule;
import org.ratpackframework.routing.Handler;
import org.ratpackframework.session.SessionManager;
import org.ratpackframework.session.store.internal.DefaultMapSessionStore;
import org.ratpackframework.session.store.internal.SessionStorageBindingHandler;

import javax.inject.Singleton;

/**
 * An extension module that provides an in memory map store for sessions, {@link MapSessionStore}.
 */
public class MapSessionsModule extends AbstractModule implements HandlerDecoratingModule {

  private final int maxEntries;
  private final int ttlMinutes;

  public MapSessionsModule(int maxEntries, int ttlMinutes) {
    this.maxEntries = maxEntries;
    this.ttlMinutes = ttlMinutes;
  }

  @Override
  protected void configure() {}

  @Provides
  @Singleton
  MapSessionStore provideMapSessionStore(SessionManager sessionManager) {
    DefaultMapSessionStore defaultMapSessionStore = new DefaultMapSessionStore(maxEntries, ttlMinutes);
    sessionManager.addSessionListener(defaultMapSessionStore);
    return defaultMapSessionStore;
  }

  public Handler decorate(Injector injector, Handler handler) {
    return new SessionStorageBindingHandler(handler);
  }
}