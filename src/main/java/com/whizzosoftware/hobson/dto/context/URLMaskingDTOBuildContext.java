/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.ImmutableHobsonVariable;

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
    public Collection<HobsonVariable> getDeviceVariables(DeviceContext dctx) {
        List<HobsonVariable> results = new ArrayList<>();
        for (HobsonVariable v : variableManager.getDeviceVariables(dctx)) {
            results.add(createStubVariableIfNecessary(v));
        }
        return results;
    }

    @Override
    public HobsonVariable getDeviceVariable(DeviceContext dctx, String name) {
        return createStubVariableIfNecessary(variableManager.getVariable(VariableContext.create(dctx, name)));
    }

    private HobsonVariable createStubVariableIfNecessary(HobsonVariable v) {
        if (v != null && v.hasMediaType()) {
            return new ImmutableHobsonVariable(
                v.getContext(),
                v.getMask(),
                "MASKED",
                v.getLastUpdate(),
                v.getMediaType()
            );
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
