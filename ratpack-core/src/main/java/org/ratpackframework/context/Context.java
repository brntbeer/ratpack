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

package org.ratpackframework.context;

import org.ratpackframework.api.Nullable;

/**
 * An object that can potentially provide objects of given types.
 *
 * A context object services as a kind of service locator. A context can be requested to provide an object of a certain type.
 * <p>
 * Context objects are not thread safe.
 *
 * @see org.ratpackframework.handling.Exchange#getContext()
 */
public interface Context {

  /**
   * Provides an object of the specified type, or throws an exception if no object of that type is available.
   *
   * @param type The type of the object to provide
   * @param <T> The type of the object to provide
   * @return An object of the specified type
   * @throws NotInContextException If no object of this type can be returned
   */
  <T> T get(Class<T> type) throws NotInContextException;

  /**
   * Does the same thing as {@link #get(Class)}, except returns null instead of throwing an exception.
   *
   * @param type The type of the object to provide
   * @param <T> The type of the object to provide
   * @return An object of the specified type, or null if no object of this type is available.
   */
  @Nullable
  <T> T maybeGet(Class<T> type);

  /**
   * Constructs a new context, which provides the given object (by its concrete type) in addition to the existing objects of this context.
   * <p>
   * The given object will take precedence over any existing object in this context.
   * <p>
   * This does not change <b>this</b> context.
   *
   * @param object The object to add to the copy of this context
   * @return A new context with the given object in addition to the objects of this context
   */
  Context plus(Object object);

  /**
   * Constructs a new context, which provides the given object (by the given type) in addition to the existing objects of this context.
   * <p>
   * The given object will take precedence over any existing object in this context.
   * <p>
   * The object will only be retrievable by the type that is given.
   * <p>
   * This does not change <b>this</b> context.
   *
   * @param type The advertised type of the object
   * @param <T> The concrete type of the object
   * @param object The object to add to the copy of this context
   * @return A new context with the given object in addition to the objects of this context
   */
  <T> Context plus(Class<? super T> type, T object);

}
