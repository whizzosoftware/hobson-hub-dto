/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.user.HobsonUser;
import org.json.JSONObject;

public class PersonDTO extends ThingDTO {
    private String givenName;
    private String familyName;
    private ItemListDTO hubs;

    public PersonDTO(HobsonUser user, LinkProvider links) {
        this.givenName = user.getFirstName();
        this.familyName = user.getLastName();

        setId(links.createUserLink(user.getId()));
        setName(this.givenName + " " + this.familyName);

        hubs = new ItemListDTO(links.createHubsLink(user.getId()));
    }

    public PersonDTO(String id, LinkProvider links) {
        setId(links.createUserLink(id));
    }

    public String getMediaType() {
        return "application/vnd.hobson.person";
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public ItemListDTO getHubs() {
        return hubs;
    }

    public void setHubs(ItemListDTO hubs) {
        this.hubs = hubs;
    }

    public JSONObject toJSON(LinkProvider links) {
        JSONObject json = super.toJSON(links);
        if (givenName != null && familyName != null) {
            json.put("givenName", getGivenName());
            json.put("familyName", getFamilyName());
        }
        if (hubs != null) {
            json.put("hubs", hubs.toJSON(links));
        }
        return json;
    }
}
