/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class ListItemDTO extends ThingDTO {
    private ThingDTO item;
    private Integer position;

    public ListItemDTO(ThingDTO item) {
        this.item = item;
    }

    public ThingDTO getItem() {
        return item;
    }

    public void setItem(ThingDTO item) {
        this.item = item;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String getMediaType() {
        return MediaTypes.LIST_ITEM;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (item != null) {
            json.put(JSONAttributes.ITEM, item.toJSON());
        }
        if (position != null) {
            json.put(JSONAttributes.POSITION, position);
        }
        return json;
    }
}
