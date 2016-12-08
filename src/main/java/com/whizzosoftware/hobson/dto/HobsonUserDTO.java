/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.user.HobsonRole;
import com.whizzosoftware.hobson.api.user.HobsonUser;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.hub.HobsonHubDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class HobsonUserDTO extends ThingDTO {
    private String userId;
    private String password;
    private UserAccountDTO account;
    private ItemListDTO dataStreams;
    private String familyName;
    private String givenName;
    private Collection<HobsonRole> roles;
    private ItemListDTO hubs;

    private HobsonUserDTO(String id) {
        super(id);
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
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

    public Collection<HobsonRole> getRoles() {
        return roles;
    }

    public ItemListDTO getHubs() {
        return hubs;
    }

    public ItemListDTO getDataStreams() {
        return dataStreams;
    }

    public void setHubs(ItemListDTO hubs) {
        this.hubs = hubs;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (userId != null) {
            json.put(JSONAttributes.USER_ID, userId);
        }
        if (givenName != null && familyName != null) {
            json.put(JSONAttributes.GIVEN_NAME, givenName);
            json.put(JSONAttributes.FAMILY_NAME, familyName);
        }
        if (hubs != null) {
            json.put(JSONAttributes.HUBS, hubs.toJSON());
        }
        if (account != null) {
            json.put(JSONAttributes.ACCOUNT, account.toJSON());
        }
        if (dataStreams != null) {
            json.put("dataStreams", dataStreams.toJSON());
        }
        if (roles != null) {
            JSONArray a = new JSONArray();
            for (HobsonRole r : roles) {
                a.put(r.name());
            }
            json.put("roles", a);
        }
        return json;
    }

    static public class Builder {
        private HobsonUserDTO dto;

        public Builder(String id) {
            this.dto = new HobsonUserDTO(id);
        }

        public Builder(DTOBuildContext ctx, HobsonUser user, Collection<String> hubs, boolean supportsDataStreams, boolean showDetails) {
            ExpansionFields expansions = ctx.getExpansionFields();
            dto = new HobsonUserDTO(ctx.getIdProvider().createPersonId(user.getId()));
            if (showDetails) {
                dto.userId = user.getId();
                dto.givenName = user.getGivenName();
                dto.familyName = user.getFamilyName();
                dto.setName(dto.givenName + " " + dto.familyName);
                dto.roles = user.getRoles();
                dto.hubs = new ItemListDTO(ctx.getIdProvider().createUserHubsId(user.getId()));
                if (supportsDataStreams) {
                    dto.dataStreams = new ItemListDTO(ctx.getIdProvider().createDataStreamsId());
                }
                if (expansions.has(JSONAttributes.HUBS)) {
                    expansions.pushContext(JSONAttributes.HUBS);
                    boolean showHubDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    if (hubs != null) {
                        for (String hubId : hubs) {
                            HobsonHub hub = ctx.getHub(HubContext.create(hubId));
                            dto.hubs.add(new HobsonHubDTO.Builder(ctx, hub, showHubDetails).build());
                        }
                    }
                    expansions.popContext();
                    expansions.popContext();
                }
            }
        }

        public Builder(JSONObject json) {
            dto = new HobsonUserDTO(json.getString(JSONAttributes.USER_ID));
            dto.userId = json.getString(JSONAttributes.USER_ID);
            dto.givenName = json.getString(JSONAttributes.GIVEN_NAME);
            dto.familyName = json.getString(JSONAttributes.FAMILY_NAME);
            dto.setName(dto.givenName + " " + dto.familyName);
            dto.password = json.getString(JSONAttributes.PASSWORD);
            if (json.has(JSONAttributes.ROLES)) {
                dto.roles = new ArrayList<>();
                JSONArray ja = json.getJSONArray(JSONAttributes.ROLES);
                for (int i=0; i < ja.length(); i++) {
                    dto.roles.add(HobsonRole.valueOf(ja.getString(i)));
                }
            }
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

        public Builder dataStreams(ItemListDTO dataStreams) {
            dto.dataStreams = dataStreams;
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

        public Builder roles(Collection<HobsonRole> roles) {
            dto.roles = roles;
            return this;
        }

        public HobsonUserDTO build() {
            return dto;
        }
    }
}
