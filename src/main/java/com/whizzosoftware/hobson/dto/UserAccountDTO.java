/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.user.UserAccount;
import org.json.JSONObject;

public class UserAccountDTO {
    private long expiration;
    private boolean hasAvailableHubs;

    private UserAccountDTO(long expiration, boolean hasAvailableHubs) {
        this.expiration = expiration;
        this.hasAvailableHubs = hasAvailableHubs;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("expiration", expiration);
        json.put("hasAvailableHubs", hasAvailableHubs);
        return json;
    }

    public static class Builder {
        private UserAccountDTO dto;

        public Builder(long expiration, boolean hasAvailableHubs) {
            dto = new UserAccountDTO(expiration, hasAvailableHubs);
        }

        public Builder(UserAccount userAccount) {
            dto.expiration = userAccount.getExpiration();
            dto.hasAvailableHubs = userAccount.hasAvailableHubs();
        }

        public UserAccountDTO build() {
            return dto;
        }
    }
}
