/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.hub.HubContext;
import com.whizzosoftware.hobson.api.user.HobsonUser;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.hub.HobsonHubDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonUserDTO extends ThingDTO {
    private UserAccountDTO account;
    private ItemListDTO dataStreams;
    private String familyName;
    private String givenName;
    private ItemListDTO hubs;

    private HobsonUserDTO(String id) {
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

    public ItemListDTO getDataStreams() {
        return dataStreams;
    }

    public void setHubs(ItemListDTO hubs) {
        this.hubs = hubs;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (givenName != null && familyName != null) {
            json.put(JSONAttributes.GIVEN_NAME, getGivenName());
            json.put(JSONAttributes.FAMILY_NAME, getFamilyName());
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
        return json;
    }

    static public class Builder {
        private HobsonUserDTO dto;

        public Builder(String id) {
            this.dto = new HobsonUserDTO(id);
        }

        public Builder(DTOBuildContext ctx, HobsonUser user, boolean supportsDataStreams, boolean showDetails) {
            ExpansionFields expansions = ctx.getExpansionFields();
            dto = new HobsonUserDTO(ctx.getIdProvider().createPersonId(user.getId()));
            if (showDetails) {
                dto.givenName = user.getGivenName();
                dto.familyName = user.getFamilyName();
                dto.setName(dto.givenName + " " + dto.familyName);
                if (user.isRemote()) {
                    dto.account = new UserAccountDTO.Builder(user.getAccount()).build();
                }
                dto.hubs = new ItemListDTO(ctx.getIdProvider().createUserHubsId(user.getId()));
                if (supportsDataStreams) {
                    dto.dataStreams = new ItemListDTO(ctx.getIdProvider().createDataStreamsId(user.getId()));
                }
                if (expansions.has(JSONAttributes.HUBS)) {
                    expansions.pushContext(JSONAttributes.HUBS);
                    boolean showHubDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (String hubId : user.getAccount().getHubs()) {
                        HobsonHub hub = ctx.getHub(HubContext.create(user.getId(), hubId));
                        dto.hubs.add(new HobsonHubDTO.Builder(ctx, hub, showHubDetails).build());
                    }
                    expansions.popContext();
                    expansions.popContext();
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

        public HobsonUserDTO build() {
            return dto;
        }
    }
}
