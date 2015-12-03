/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.presence;

import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.presence.MockPresenceManager;
import com.whizzosoftware.hobson.api.presence.PresenceEntity;
import com.whizzosoftware.hobson.api.presence.PresenceEntityContext;
import com.whizzosoftware.hobson.api.presence.PresenceLocationContext;
import com.whizzosoftware.hobson.dto.ExpansionFields;
import com.whizzosoftware.hobson.dto.MockIdProvider;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.junit.Test;
import static org.junit.Assert.*;

public class PresenceEntityDTOTest {
    @Test
    public void testConstructorWithNoExpansion() {
        MockPresenceManager presenceManager = new MockPresenceManager();
        PresenceEntity pe = new PresenceEntity(PresenceEntityContext.createLocal("id1"), "name1");
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setPresenceEntityId("foo");

        PresenceEntityDTO dto = new PresenceEntityDTO.Builder(
            new ManagerDTOBuildContext.Builder().presenceManager(presenceManager).idProvider(idProvider).build(),
            pe,
            false
        ).build();
        assertEquals("foo", dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getLastUpdate());
        assertNull(dto.getLocation());
    }

    @Test
    public void testConstructorWithItemExpansion() {
        PresenceEntityContext ctx = PresenceEntityContext.createLocal("id1");

        MockPresenceManager presenceManager = new MockPresenceManager();
        PresenceLocationContext ctx2 = presenceManager.addPresenceLocation(HubContext.createLocal(), "location1", 1.0, 2.0, 3.0, null, null);
        presenceManager.updatePresenceEntityLocation(ctx, ctx2);

        PresenceEntity pe = new PresenceEntity(ctx, "name1", 100L);
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setPresenceEntityId("foo");
        idProvider.setPresenceLocationId("bar");

        // test with just item expansion
        PresenceEntityDTO dto = new PresenceEntityDTO.Builder(new ManagerDTOBuildContext.Builder().presenceManager(presenceManager).expansionFields(new ExpansionFields("")).idProvider(idProvider).build(), pe, true).build();
        assertEquals("foo", dto.getId());
        assertEquals("name1", dto.getName());
        assertEquals(100L, (long) dto.getLastUpdate());
        assertNotNull(dto.getLocation());
        assertEquals("bar", dto.getLocation().getId());
        assertNull(dto.getLocation().getName());

        // test with both item and location expansions
        dto = new PresenceEntityDTO.Builder(new ManagerDTOBuildContext.Builder().presenceManager(presenceManager).expansionFields(new ExpansionFields(JSONAttributes.LOCATION)).idProvider(idProvider).build(), pe, true).build();
        assertEquals("foo", dto.getId());
        assertEquals("name1", dto.getName());
        assertEquals(100L, (long) dto.getLastUpdate());
        assertNotNull(dto.getLocation());
        assertEquals("bar", dto.getLocation().getId());
        assertEquals("location1", dto.getLocation().getName());
        assertEquals(1.0, dto.getLocation().getLatitude(), 0.0);
        assertEquals(2.0, dto.getLocation().getLongitude(), 0.0);
        assertEquals(3.0, dto.getLocation().getRadius(), 0.0);
    }

    @Test
    public void testConstructorWithNoLocation() {
        PresenceEntity pe = new PresenceEntity(PresenceEntityContext.createLocal("entity1"), "entity");
        MockPresenceManager manager = new MockPresenceManager();
        MockIdProvider idProvider = new MockIdProvider();
        idProvider.setPresenceEntityId("entityId1");
        PresenceEntityDTO dto = new PresenceEntityDTO.Builder(new ManagerDTOBuildContext.Builder().presenceManager(manager).expansionFields(new ExpansionFields("")).idProvider(idProvider).build(), pe, true).build();
        assertEquals("entityId1", dto.getId());
        assertEquals("entity", dto.getName());
        assertNotNull(dto.getLocation());
        assertNull(dto.getLocation().getId());
    }
}
