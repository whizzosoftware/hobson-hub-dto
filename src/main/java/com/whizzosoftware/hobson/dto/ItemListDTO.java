/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListDTO extends ThingDTO {
    private List<ListItemDTO> itemListElement;
    private Integer numberOfItems;

    public ItemListDTO(String id) {
        super(id);
    }

    public List<ListItemDTO> getItemListElement() {
        return itemListElement;
    }

    public Integer getNumberOfItems() {
        return numberOfItems;
    }

    public void updateNumberOfItems() {
        numberOfItems = (itemListElement != null) ? itemListElement.size() : 0;
    }

    public void add(ThingDTO item) {
        add(new ListItemDTO(item));
    }

    public void add(ListItemDTO item) {
        if (itemListElement == null) {
            itemListElement = new ArrayList<>();
        }
        itemListElement.add(item);
        updateNumberOfItems();
    }

    @Override
    public String getMediaType() {
        return "application/vnd.hobson.itemList";
    }

    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        if (numberOfItems != null) {
            json.put("numberOfItems", numberOfItems);
        }
        if (itemListElement != null) {
            JSONArray array = new JSONArray();
            for (ListItemDTO li : itemListElement) {
                array.put(li.toJSON());
            }
            json.put("itemListElement", array);
        }
        return json;
    }

}
