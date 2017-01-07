package com.whizzosoftware.hobson.dto.action;

import com.whizzosoftware.hobson.api.action.ActionClass;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassContext;
import com.whizzosoftware.hobson.dto.context.ManagerDTOBuildContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActionClassDTOTest {
    @Test
    public void testJSONDetails() {
        ActionClass ac = new ActionClass(PropertyContainerClassContext.create("local", "plugin", "device", "cc"), "ac", "acd", true, 2000L);
        PropertyContainerClassDTO dto = new ActionClassDTO.Builder(new ManagerDTOBuildContext(), new TemplatedId("acid", null), ac, true).build();
        JSONObject json = dto.toJSON();
        assertEquals("acid", json.getString(JSONAttributes.AID));
        assertEquals("ac", json.getString(JSONAttributes.NAME));
        assertEquals("acd", json.getString(JSONAttributes.DESCRIPTION_TEMPLATE));
        assertTrue(json.getBoolean(JSONAttributes.TASK_ACTION));
    }

    @Test
    public void testJSONNoDetails() {
        ActionClass ac = new ActionClass(PropertyContainerClassContext.create("local", "plugin", "device", "cc"), "ac", "acd", true, 2000L);
        PropertyContainerClassDTO dto = new ActionClassDTO.Builder(new ManagerDTOBuildContext(), new TemplatedId("acid", null), ac, false).build();
        JSONObject json = dto.toJSON();
        assertEquals("acid", json.getString(JSONAttributes.AID));
        assertFalse(json.has(JSONAttributes.NAME));
    }
}
