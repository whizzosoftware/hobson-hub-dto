/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONObject;

public class PasswordChangeDTO implements JSONProducer {
    private String currentPassword;
    private String newPassword;

    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public PasswordChangeDTO(JSONObject json) {
        this.currentPassword = json.getString("currentPassword");
        this.newPassword = json.getString("newPassword");
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.PASSWORD_CHANGE;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("currentPassword", currentPassword);
        json.put("newPassword", newPassword);
        return json;
    }
}
