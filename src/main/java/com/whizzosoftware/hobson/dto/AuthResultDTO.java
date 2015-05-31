/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.ExpansionFields;
import com.whizzosoftware.hobson.api.user.HobsonUser;
import org.json.JSONObject;

public class AuthResultDTO {
    private String token;
    private PersonDTO user;

    public AuthResultDTO(String token, HobsonUser user, ExpansionFields expansions, LinkProvider links) {
        this.token = token;
        this.user = expansions.has("user") ? new PersonDTO(user, links) : new PersonDTO(user.getId(), links);
    }

    public String getMediaType() {
        return "application/vnd.hobson.authResult";
    }

    public PersonDTO getUser() {
        return user;
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = new JSONObject();
        json.put("token", token);
        json.put("user", user.toJSON(links));
        return json;
    }
}
