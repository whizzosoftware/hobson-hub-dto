/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.plugin.PluginContext;
import com.whizzosoftware.hobson.api.task.TaskContext;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import static org.junit.Assert.*;

public class TaskConditionSetDTOTest {
//    @Test
//    public void testJSONConstructor() {
//        MockLinkProvider links = new MockLinkProvider();
//        TaskActionSetDTO dto = new TaskActionSetDTO(new JSONObject(new JSONTokener("{\"hub\":{\"@id\":\"hubLink\"},\"actions\":[{\"actionClass\":{\"@id\":\"actionClassLink\"},\"properties\":{\"foo\":\"bar\"}}]}")), links);
//        assertNotNull(dto.getActions());
//        assertEquals(1, dto.getActions().size());
//        assertNull(dto.getActions().get(0).getId());
//        assertEquals("local", dto.getActions().get(0).getContainerClass().getContext().getUserId());
//        assertEquals("local", dto.getActions().get(0).getContainerClass().getContext().getHubId());
//        assertEquals("plugin1", dto.getActions().get(0).getContainerClass().getContext().getPluginId());
//        assertEquals("actionclass1", dto.getActions().get(0).getContainerClass().getContext().getActionClassId());
//    }
//
//    public class MockLinkProvider implements LinkProvider {
//        @Override
//        public HubContext createHubContext(String link) {
//            return null;
//        }
//
//        @Override
//        public String createTaskLink(TaskContext ctx) {
//            return null;
//        }
//
//        @Override
//        public TaskContext createTaskContext(String string) {
//            return null;
//        }
//
//        @Override
//        public String createTaskConditionClassesLink(HubContext ctx) {
//            return null;
//        }
//
//        @Override
//        public String createTaskConditionClassLink(TaskConditionClassContext ctx) {
//            return null;
//        }
//
//        @Override
//        public TaskConditionClassContext createTaskConditionClassContext(String link) {
//            return null;
//        }
//
//        @Override
//        public String createTaskActionClassesLink(HubContext ctx) {
//            return null;
//        }
//
//        @Override
//        public String createTaskActionClassLink(TaskActionClassContext ctx) {
//            return null;
//        }
//
//        @Override
//        public TaskActionClassContext createTaskActionClassContext(String link) {
//            return TaskActionClassContext.createLocal("plugin1", "actionclass1");
//        }
//
//        @Override
//        public String createTaskActionSetLink(HubContext ctx, String actionSetId) {
//            return null;
//        }
//
//        @Override
//        public PluginContext createPluginContext(String link) {
//            return null;
//        }
//
//        @Override
//        public String createPluginLink(PluginContext ctx) {
//            return null;
//        }
//
//        @Override
//        public String createUserLink(String id) {
//            return null;
//        }
//
//        @Override
//        public String createHubLink(HubContext context) {
//            return null;
//        }
//
//        @Override
//        public String createHubsLink(String userId) {
//            return null;
//        }
//    }
}
