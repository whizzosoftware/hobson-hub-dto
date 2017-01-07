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

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListDTO extends ThingDTO {
    private List<ListItemDTO> itemListElement = new ArrayList<>();
    private boolean forceDetails;

    public ItemListDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
    }

    public ItemListDTO(TemplatedIdBuildContext ctx, TemplatedId id, boolean forceDetails) {
        super(ctx, id);
        this.forceDetails = forceDetails;
    }

    public ItemListDTO(String id) {
        this(id, false);
    }

    public ItemListDTO(String id, boolean forceDetails) {
        super(id);
        this.forceDetails = forceDetails;
    }

    public List<ListItemDTO> getItemListElement() {
        return itemListElement;
    }

    public Integer getNumberOfItems() {
        return itemListElement.size();
    }

    public void add(EntityDTO item) {
        add(new ListItemDTO(item));
    }

    public void add(ListItemDTO item) {
        itemListElement.add(item);
    }

    @Override
    public String getMediaType() {
        return MediaTypes.ITEM_LIST;
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (forceDetails || itemListElement.size() > 0) {
            json.put(JSONAttributes.NUMBER_OF_ITEMS, itemListElement.size());
            JSONArray array = new JSONArray();
            for (ListItemDTO li : itemListElement) {
                array.put(li.toJSON());
            }
            json.put(JSONAttributes.ITEM_LIST_ELEMENT, array);
        }
        return json;
    }
}
