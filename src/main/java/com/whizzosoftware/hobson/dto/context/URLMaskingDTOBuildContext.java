/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.variable.DeviceVariableContext;
import com.whizzosoftware.hobson.api.variable.DeviceVariableDescriptor;
import com.whizzosoftware.hobson.api.variable.DeviceVariableState;

import java.util.Collection;

/**
 * An implementation of DTOBuildContext that uses Hobson manager objects for data and masks any variable media URL
 * values. This is done for security/privacy purposes since internal URLs are useless outside the local network.
 *
 * @author Dan Noguerol
 */
public class URLMaskingDTOBuildContext extends ManagerDTOBuildContext {
    @Override
    public Collection<DeviceVariableDescriptor> getDeviceVariables(DeviceContext dctx) {
        return deviceManager.getDevice(dctx).getVariables();
    }

    @Override
    public DeviceVariableState getDeviceVariableState(DeviceVariableContext vctx) {
        return createStubVariableIfNecessary(deviceManager.getDevice(vctx.getDeviceContext()).getVariable(vctx.getName()), deviceManager.getDeviceVariable(vctx));
    }

    private DeviceVariableState createStubVariableIfNecessary(DeviceVariableDescriptor d, DeviceVariableState state) {
        if (d != null && d.hasMediaType()) {
            return new DeviceVariableState(state.getContext(), "MASKED", state.getLastUpdate());
        } else {
            return state;
        }
    }

    public static final class Builder extends ManagerDTOBuildContext.Builder {
        public Builder() {
            ctx = new URLMaskingDTOBuildContext();
        }
    }
}
