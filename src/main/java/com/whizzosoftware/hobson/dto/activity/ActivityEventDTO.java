/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.activity;

import com.whizzosoftware.hobson.dto.ThingDTO;

public class ActivityEventDTO extends ThingDTO {
    private String name;
    private Long timestamp;

    public ActivityEventDTO(String name, Long timestamp) {
        this.name = name;
        this.timestamp = timestamp;
    }

    @Override
    public String getMediaType() {
        return "application/hobson.vnd.event";
    }

    public String getName() {
        return name;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
