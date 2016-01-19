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
import org.junit.Test;
import static org.junit.Assert.*;

public class URLMaskingDTOBuildContextTest {
    @Test
    public void testMasking() {
        DeviceContext dctx = DeviceContext.createLocal("plugin1", "device1");
        MockVariableManager vm = new MockVariableManager();
        vm.publishVariable(VariableContext.create(dctx, VariableConstants.OUTDOOR_TEMP_F), 57.0, HobsonVariable.Mask.READ_ONLY, null);
        vm.publishVariable(VariableContext.create(dctx, VariableConstants.IMAGE_STATUS_URL), "http://www.foo.com", HobsonVariable.Mask.READ_ONLY, null, VariableMediaType.IMAGE_JPG);

        DTOBuildContext ctx = new URLMaskingDTOBuildContext.Builder().
            variableManager(vm).
            build();

        HobsonVariable v = ctx.getDeviceVariable(dctx, VariableConstants.OUTDOOR_TEMP_F);
        assertEquals(57.0, v.getValue());
        v = ctx.getDeviceVariable(dctx, VariableConstants.IMAGE_STATUS_URL);
        assertEquals("MASKED", v.getValue());
    }
}
