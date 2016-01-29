/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.DevicePassport;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.persist.ContextPathIdProvider;
import com.whizzosoftware.hobson.api.persist.IdProvider;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import org.junit.Test;
import static org.junit.Assert.*;

public class DevicePassportDTOTest {
    @Test
    public void testObjectConstructor() {
        HubContext hctx = HubContext.createLocal();
        IdProvider idProvider = new ContextPathIdProvider();
        DTOBuildContext ctx = new ManagerDTOBuildContext.Builder().idProvider(idProvider).build();
        DevicePassport dp = new DevicePassport(hctx, "dp1", "device1", System.currentTimeMillis());
        DevicePassportDTO dto = new DevicePassportDTO.Builder(ctx, dp, false, false).build();
        assertEquals("users:local:hubs:local:passports:dp1", dto.getId());
    }
}
