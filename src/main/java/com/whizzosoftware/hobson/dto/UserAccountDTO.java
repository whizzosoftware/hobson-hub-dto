/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.HobsonRuntimeException;
import com.whizzosoftware.hobson.api.user.UserAccount;
import org.json.JSONObject;

public class UserAccountDTO {
    private Long expiration;
    private boolean hasAvailableHubs;

    private UserAccountDTO(Long expiration, boolean hasAvailableHubs) {
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

        public Builder(Long expiration, boolean hasAvailableHubs) {
            dto = new UserAccountDTO(expiration, hasAvailableHubs);
        }

        public Builder(UserAccount userAccount) {
            if (userAccount == null) {
                throw new HobsonRuntimeException("No user account information");
            }
            dto = new UserAccountDTO(userAccount.getExpiration(), userAccount.hasAvailableHubs());
        }

        public UserAccountDTO build() {
            return dto;
        }
    }
}
