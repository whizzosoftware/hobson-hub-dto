/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.dto.ThingDTO;
import org.json.JSONObject;

/**
 * A data transfer object for device boostrap information.
 *
 * @author Dan Noguerol
 */
public class DeviceBootstrapDTO extends ThingDTO {
    private String deviceId;
    private Long creationTime;
    private Long bootstrapTime;
    private String secret;

    private DeviceBootstrapDTO(String id) {
        super(id);
    }

    @Override
    public String getMediaType() {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("deviceId", deviceId);
        json.put("creationTime", creationTime);
        json.put("bootstrapTime", bootstrapTime);
        json.put("secret", secret);
        return json;
    }

    static public class Builder {
        private DeviceBootstrapDTO dto;

        public Builder(String id) {
            dto = new DeviceBootstrapDTO(id);
        }

        public Builder deviceId(String deviceId) {
            dto.deviceId = deviceId;
            return this;
        }

        public Builder creationTime(Long creationTime) {
            dto.creationTime = creationTime;
            return this;
        }

        public Builder bootstrapTime(Long bootstrapTime) {
            dto.bootstrapTime = bootstrapTime;
            return this;
        }

        public Builder secret(String secret) {
            dto.secret = secret;
            return this;
        }

        public DeviceBootstrapDTO build() {
            return dto;
        }
    }
}
