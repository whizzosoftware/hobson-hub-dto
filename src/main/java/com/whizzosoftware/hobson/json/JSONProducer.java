/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json;

import org.json.JSONObject;

public interface JSONProducer {
    /**
     * Returns the media type (MIME type) associated with the JSON representation.
     *
     * @return a String
     */
    String getMediaType();

    /**
     * Returns the JSON-specific media type (MIME type) associated with the JSON representation (i.e. suffixed with
     * a "+json").
     *
     * @return a String
     */
    String getJSONMediaType();

    /**
     * Returns a JSON representation.
     *
     * @return a JSONObject
     */
    JSONObject toJSON();
}
