/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto.presence;

import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class PresenceLocationDTO extends ThingDTO {
    private Double latitude;
    private Double longitude;
    private Double radius;
    private Integer beaconMajor;
    private Integer beaconMinor;

    private PresenceLocationDTO(String id) {
        super(id);
    }

    private PresenceLocationDTO(JSONObject json) {
        super(json);
        if (json.has(JSONAttributes.LATITUDE)) {
            this.latitude = json.getDouble(JSONAttributes.LATITUDE);
        }
        if (json.has(JSONAttributes.LONGITUDE)) {
            this.longitude = json.getDouble(JSONAttributes.LONGITUDE);
        }
        if (json.has(JSONAttributes.RADIUS)) {
            this.radius = json.getDouble(JSONAttributes.RADIUS);
        }
        if (json.has(JSONAttributes.BEACON_MAJOR)) {
            this.beaconMajor = json.getInt(JSONAttributes.BEACON_MAJOR);
        }
        if (json.has(JSONAttributes.BEACON_MINOR)) {
            this.beaconMinor = json.getInt(JSONAttributes.BEACON_MINOR);
        }
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getRadius() {
        return radius;
    }

    public Integer getBeaconMajor() {
        return beaconMajor;
    }

    public Integer getBeaconMinor() {
        return beaconMinor;
    }

    @Override
    public String getMediaType() {
        return null;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.LATITUDE, latitude);
        json.put(JSONAttributes.LONGITUDE, longitude);
        json.put(JSONAttributes.RADIUS, radius);
        json.put(JSONAttributes.BEACON_MAJOR, beaconMajor);
        json.put(JSONAttributes.BEACON_MINOR, beaconMinor);
        return json;
    }

    public static class Builder {
        private PresenceLocationDTO dto;

        public Builder(String id) {
            dto = new PresenceLocationDTO(id);
        }

        public Builder(JSONObject json) {
            dto = new PresenceLocationDTO(json);
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder latitude(Double latitude) {
            dto.latitude = latitude;
            return this;
        }

        public Builder longitude(Double longitude) {
            dto.longitude = longitude;
            return this;
        }

        public Builder radius(Double radius) {
            dto.radius = radius;
            return this;
        }

        public Builder beaconMajor(Integer beaconMajor) {
            dto.beaconMajor = beaconMajor;
            return this;
        }

        public Builder beaconMinor(Integer beaconMinor) {
            dto.beaconMinor = beaconMinor;
            return this;
        }

        public PresenceLocationDTO build() {
            return dto;
        }
    }
}
