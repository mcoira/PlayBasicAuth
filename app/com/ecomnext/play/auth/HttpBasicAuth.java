/*
 * Copyright (C) Ecomnext <http://www.ecomnext.com>
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
package com.ecomnext.play.auth;

import play.mvc.With;

import java.lang.annotation.*;

/**
 * Custom annotation to offer HTTP Basic Authentication to any method of the API.
 * In order to use it you will have to annotate the method to secure it.
 *
 * @author Miguel Coira
 */
@With(HttpBasicAuthAction.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Inherited
@Documented
public @interface HttpBasicAuth {
}
