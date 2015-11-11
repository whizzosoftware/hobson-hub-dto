/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.hub.HobsonHub;
import com.whizzosoftware.hobson.api.user.HobsonUser;
import com.whizzosoftware.hobson.dto.hub.HobsonHubDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class HobsonUserDTO extends ThingDTO {
    private String givenName;
    private String familyName;
    private ItemListDTO hubs;
    private UserAccountDTO account;

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
        private HobsonUserDTO dto;

        public Builder(String id) {
            this.dto = new HobsonUserDTO(id);
        }

        public Builder(DTOBuildContext ctx, HobsonUser user, boolean showDetails) {
            ExpansionFields expansions = ctx.getExpansionFields();
            dto = new HobsonUserDTO(ctx.getIdProvider().createPersonId(user.getId()));
            if (showDetails) {
                dto.givenName = user.getGivenName();
                dto.familyName = user.getFamilyName();
                dto.setName(dto.givenName + " " + dto.familyName);
                if (user.isRemote()) {
                    dto.account = new UserAccountDTO.Builder(user.getAccount()).build();
                }
                dto.hubs = new ItemListDTO(ctx.getIdProvider().createHubsId(user.getId()));
                if (expansions.has(JSONAttributes.HUBS)) {
                    expansions.pushContext(JSONAttributes.HUBS);
                    boolean showHubDetails = expansions.has(JSONAttributes.ITEM);
                    expansions.pushContext(JSONAttributes.ITEM);
                    for (HobsonHub hub : ctx.getHubManager().getHubs(user.getId())) {
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
