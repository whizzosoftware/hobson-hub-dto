/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.api.device.DeviceContext;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.MockDeviceManager;
import com.whizzosoftware.hobson.api.device.MockDeviceProxy;
import com.whizzosoftware.hobson.api.plugin.MockHobsonPlugin;
import com.whizzosoftware.hobson.api.variable.*;
import io.netty.util.concurrent.Future;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class URLMaskingDTOBuildContextTest {
    @Test
    public void testMasking() throws Exception {
        DeviceContext dctx = DeviceContext.createLocal("plugin1", "device1");
        final DeviceVariableContext vctx1 = DeviceVariableContext.create(dctx, VariableConstants.OUTDOOR_TEMP_F);
        final DeviceVariableContext vctx2 = DeviceVariableContext.create(dctx, VariableConstants.IMAGE_STATUS_URL);

        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1");
        final MockDeviceManager dm = new MockDeviceManager();
        MockDeviceProxy proxy = new MockDeviceProxy(plugin, "device1", DeviceType.LIGHTBULB) {
            public DeviceVariableDescription[] createVariableDescriptions() {
                return new DeviceVariableDescription[]{
                    new DeviceVariableDescription(vctx1, DeviceVariableDescription.Mask.READ_ONLY),
                    new DeviceVariableDescription(vctx2, DeviceVariableDescription.Mask.READ_ONLY, null, VariableMediaType.IMAGE_JPG)
                };
            }
        };
        Future f = dm.publishDevice(plugin.getContext(), proxy, null).await();
        assertTrue(f.isSuccess());
        f = dm.setDeviceVariable(vctx1, 57.0).await();
        assertTrue(f.isSuccess());
        f = dm.setDeviceVariable(vctx2, "http://www.foo.com").await();
        assertTrue(f.isSuccess());
        DTOBuildContext ctx = new URLMaskingDTOBuildContext.Builder().deviceManager(dm).build();

        DeviceVariable v = ctx.getDeviceVariable(vctx1);
        assertEquals(57.0, v.getValue());
        v = ctx.getDeviceVariable(vctx2);
        assertEquals("MASKED", v.getValue());
    }
}
