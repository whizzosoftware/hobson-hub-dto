/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.variable.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An implementation of DTOBuildContext that uses Hobson manager objects for data and masks any variable media URL
 * values. This is done for security/privacy purposes since internal URLs are useless outside the local network.
 *
 * @author Dan Noguerol
 */
public class URLMaskingDTOBuildContext extends ManagerDTOBuildContext {
    @Override
    public Collection<DeviceVariable> getDeviceVariables(DeviceContext dctx) {
        List<DeviceVariable> results = new ArrayList<>();
        for (DeviceVariable v : deviceManager.getDeviceVariables(dctx)) {
            results.add(createStubVariableIfNecessary(v));
        }
        return results;
    }

    @Override
    public DeviceVariable getDeviceVariable(DeviceVariableContext vctx) {
        return createStubVariableIfNecessary(deviceManager.getDeviceVariable(vctx));
    }

    private DeviceVariable createStubVariableIfNecessary(DeviceVariable v) {
        if (v != null && v.getDescription().hasMediaType()) {
            return new DeviceVariable(v.getDescription(), "MASKED", v.getLastUpdate());
        } else {
            return v;
        }
    }

    public static final class Builder extends ManagerDTOBuildContext.Builder {
        public Builder() {
            ctx = new URLMaskingDTOBuildContext();
        }
    }
}
