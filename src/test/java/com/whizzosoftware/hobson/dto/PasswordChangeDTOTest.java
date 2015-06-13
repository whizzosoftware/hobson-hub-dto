/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PasswordChangeDTOTest {
    @Test
    public void testCompare() {
        PasswordChangeDTO dto = new PasswordChangeDTO(new JSONObject(new JSONTokener("{\"currentPassword\":\"foo\", \"newPassword\":\"bar\"}")));
        assertEquals("foo", dto.getCurrentPassword());
        assertEquals("bar", dto.getNewPassword());
    }
}
