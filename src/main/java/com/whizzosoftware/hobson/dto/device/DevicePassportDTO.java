/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.device;

import com.whizzosoftware.hobson.api.device.DevicePassport;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

/**
 * A data transfer object for device boostrap information.
 *
 * @author Dan Noguerol
 */
public class DevicePassportDTO extends ThingDTO {
    private String deviceId;
    private Long creationTime;
    private Long activationTime;
    private String secret;

    private DevicePassportDTO(String id) {
        super(id);
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public Long getActivationTime() {
        return activationTime;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.DEVICE_PASSPORT;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.DEVICE_ID, deviceId);
        json.put(JSONAttributes.CREATION_TIME, creationTime);
        json.put(JSONAttributes.ACTIVATION_TIME, activationTime);
        json.put(JSONAttributes.SECRET, secret);
        return json;
    }

    static public class Builder {
        private DevicePassportDTO dto;

        public Builder(String id) {
            dto = new DevicePassportDTO(id);
        }

        public Builder(DTOBuildContext ctx, DevicePassport dp, boolean showDetails, boolean includeSecret) {
            dto = new DevicePassportDTO(ctx.getIdProvider().createDevicePassportId(dp.getHubContext(), dp.getId()));
            if (showDetails) {
                dto.activationTime = dp.getActivationTime();
                dto.creationTime = dp.getCreationTime();
                dto.deviceId = dp.getDeviceId();
                if (includeSecret) {
                    dto.secret = dp.getSecret();
                }
            }
        }

        public Builder deviceId(String deviceId) {
            dto.deviceId = deviceId;
            return this;
        }

        public Builder creationTime(Long creationTime) {
            dto.creationTime = creationTime;
            return this;
        }

        public Builder activationTime(Long activationTime) {
            dto.activationTime = activationTime;
            return this;
        }

        public Builder secret(String secret) {
            dto.secret = secret;
            return this;
        }

        public DevicePassportDTO build() {
            return dto;
        }
    }
}
