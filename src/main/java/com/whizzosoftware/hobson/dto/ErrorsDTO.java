/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.json.JSONAttributes;
import com.whizzosoftware.hobson.json.JSONProducer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ErrorsDTO implements JSONProducer {
    private List<ErrorDTO> errors = new ArrayList<>();

    @Override
    public String getMediaType() {
        return MediaTypes.ERRORS;
    }

    @Override
    public String getJSONMediaType() {
        return getMediaType() + "+json";
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        JSONArray a = new JSONArray();
        for (ErrorDTO error : errors) {
            a.put(error.toJSON());
        }
        json.put(JSONAttributes.ERRORS, a);
        return json;
    }

    public static class Builder {
        private ErrorsDTO dto;

        public Builder() {
            dto = new ErrorsDTO();
        }

        public Builder(Integer code, String message) {
            dto = new ErrorsDTO();
            addError(code, message);
        }

        public Builder(Throwable t) {
            dto = new ErrorsDTO();
            addError(t);
        }

        public Builder addError(ErrorDTO error) {
            dto.errors.add(error);
            return this;
        }

        public Builder addError(Integer code, String message) {
            dto.errors.add(new ErrorDTO(code, message));
            return this;
        }

        public Builder addError(Throwable t) {
            dto.errors.add(new ErrorDTO(t));
            return this;
        }

        public ErrorsDTO build() {
            return dto;
        }
    }
}
