/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

public class AuthResultDTO {
    private String token;
    private HobsonUserDTO user;

    public AuthResultDTO(String token, HobsonUserDTO user) {
        this.token = token;
        this.user = user;
    }

    public String getMediaType() {
        return "application/vnd.hobson.authResult";
    }

    public HobsonUserDTO getUser() {
        return user;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("user", user.toJSON());
        return json;
    }
}
