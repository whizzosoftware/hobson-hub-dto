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
