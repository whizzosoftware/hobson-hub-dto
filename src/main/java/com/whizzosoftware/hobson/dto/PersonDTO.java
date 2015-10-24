/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONObject;

public class PersonDTO extends ThingDTO {
    private String givenName;
    private String familyName;
    private ItemListDTO hubs;
    private UserAccountDTO account;

    private PersonDTO(String id) {
        super(id);
    }

    public String getMediaType() {
        return MediaTypes.PERSON;
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

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (givenName != null && familyName != null) {
            json.put("givenName", getGivenName());
            json.put("familyName", getFamilyName());
        }
        if (hubs != null) {
            json.put("hubs", hubs.toJSON());
        }
        if (account != null) {
            json.put("account", account.toJSON());
        }
        return json;
    }

    static public class Builder {
        private PersonDTO dto;

        public Builder(String id) {
            this.dto = new PersonDTO(id);
        }

        public Builder givenName(String givenName) {
            dto.givenName = givenName;
            if (dto.givenName != null && dto.familyName != null) {
                dto.setName(dto.givenName + " " + dto.familyName);
            }
            return this;
        }

        public Builder familyName(String familyName) {
            dto.familyName = familyName;
            if (dto.givenName != null && dto.familyName != null) {
                dto.setName(dto.givenName + " " + dto.familyName);
            }
            return this;
        }

        public Builder hubs(ItemListDTO hubs) {
            dto.hubs = hubs;
            return this;
        }

        public Builder account(UserAccountDTO account) {
            dto.account = account;
            return this;
        }

        public PersonDTO build() {
            return dto;
        }
    }
}
