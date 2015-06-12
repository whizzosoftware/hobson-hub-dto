/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import com.whizzosoftware.hobson.api.HobsonRuntimeException;
import org.json.JSONObject;

public class ErrorDTO {
    private Integer code;
    private String message;

    public ErrorDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDTO(Throwable t) {
        if (t instanceof HobsonRuntimeException) {
            this.code = ((HobsonRuntimeException)t).getCode();
        } else {
            this.code = HobsonRuntimeException.CODE_INTERNAL_ERROR;
        }
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", message);
        return json;
    }
}
