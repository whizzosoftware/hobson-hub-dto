/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.hub;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;

public class HubLogDTO extends ThingDTO {
    public HubLogDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.LOG;
    }
}
