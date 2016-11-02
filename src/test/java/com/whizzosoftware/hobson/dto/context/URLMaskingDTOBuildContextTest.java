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
import com.whizzosoftware.hobson.api.event.MockEventManager;
import com.whizzosoftware.hobson.api.plugin.MockHobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.variable.*;
import io.netty.util.concurrent.Future;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class URLMaskingDTOBuildContextTest {
    @Test
    public void testMasking() throws Exception {
        DeviceContext dctx = DeviceContext.createLocal("plugin1", "device1");
        final DeviceVariableContext vctx1 = DeviceVariableContext.create(dctx, VariableConstants.OUTDOOR_TEMP_F);
        final DeviceVariableContext vctx2 = DeviceVariableContext.create(dctx, VariableConstants.IMAGE_STATUS_URL);

        final MockDeviceManager dm = new MockDeviceManager();
        MockEventManager em = new MockEventManager();
        MockHobsonPlugin plugin = new MockHobsonPlugin("plugin1", "1.0.0", "");
        plugin.setEventManager(em);
        plugin.setDeviceManager(dm);
        MockDeviceProxy proxy = new MockDeviceProxy(plugin, "device1", DeviceType.LIGHTBULB) {
            public void onStartup(String name, PropertyContainer config) {
                publishVariables(new DeviceProxyVariable(vctx1, VariableMask.READ_ONLY),new DeviceProxyVariable(vctx2, VariableMask.READ_ONLY, VariableMediaType.IMAGE_JPG));
            }
        };
        Future f = dm.publishDevice(proxy, null, null).await();
        assertTrue(f.isSuccess());
        f = dm.setDeviceVariable(vctx1, 57.0).await();
        assertTrue(f.isSuccess());
        f = dm.setDeviceVariable(vctx2, "http://www.foo.com").await();
        assertTrue(f.isSuccess());
        DTOBuildContext ctx = new URLMaskingDTOBuildContext.Builder().deviceManager(dm).build();

        DeviceVariableState v = ctx.getDeviceVariableState(vctx1);
        assertNotNull(v);
        assertEquals(57.0, v.getValue());
        v = ctx.getDeviceVariableState(vctx2);
        assertEquals("MASKED", v.getValue());
    }
}
