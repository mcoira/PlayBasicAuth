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

import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import java.nio.charset.StandardCharsets;

/**
 * This action is used in composition with others and it provides an implementation
 * of the HTTP Basic Authentication.
 *
 * There is an annotation {@link HttpBasicAuth} which facilitates the composition,
 * so you can annotate any controller's method, or any controller to protect them.
 *
 * This action will return an unauthorized (code 401) response in case the authorization
 * header is missing or wrong formatted or if either username or password are wrong.
 *
 * If both, username and password, match with a valid account the username will be put
 * into the context for further usage in other action/controller.
 */
public class HttpBasicAuthAction extends play.mvc.Action<HttpBasicAuth> {
    private static final String AUTHORIZATION_HEADER = "authorization";
    private static final String WWW_AUTHENTICATE_HEADER = "WWW-Authenticate";
    private static final String REALM = "Basic realm=\"Your API\"";

    private static final String USERNAME = "username";

    @Override
    public F.Promise<Result> call(Http.Context context) throws Throwable {
        String authHeader = context.request().getHeader(AUTHORIZATION_HEADER);

        // if no authorization header is provided,
        // then an unauthorized (401) response is returned
        if (authHeader == null) {
            context.response().setHeader(WWW_AUTHENTICATE_HEADER, REALM);
            return F.Promise.<Result>pure(unauthorized());
        }

        // The value of the header is must contain the authorization method (Basic in
        // this case), an space, and Username and password combined into a string
        // "username:password" which is encoded using the RFC2045-MIME variant of Base64.
        String auth = authHeader.substring(6); // to avoid 'Basic '
        byte[] decodedAuth = java.util.Base64.getMimeDecoder().decode(auth);
        String[] credString = new String(decodedAuth, StandardCharsets.UTF_8).split(":");

        // in case that either username or password are not present then return an
        // unauthorized (401) response
        if (credString.length != 2) {
            return F.Promise.<Result>pure(unauthorized());
        }

        final String username = credString[0];
        final String password = credString[1];

        // check username and password
        if ("username".equals(username) && "password".equals(password)) {
            context.args.put(USERNAME, username);
            return delegate.call(context);
        } else {
            return F.Promise.<Result>pure(unauthorized());
        }
    }
}
