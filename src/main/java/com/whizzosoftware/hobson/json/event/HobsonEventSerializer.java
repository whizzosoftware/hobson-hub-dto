/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.json.event;

import com.whizzosoftware.hobson.api.event.HobsonEvent;
import org.json.JSONObject;

/**
 * An interface for classes that can serialize HobsonEvent objects.
 *
 * @author Dan Noguerol
 */
public interface HobsonEventSerializer {
    String PROP_EVENT = "event";

    boolean canMarshal(HobsonEvent event);
    JSONObject marshal(HobsonEvent event);
    boolean canUnmarshal(JSONObject json);
    HobsonEvent unmarshal(JSONObject json);
}
