/*******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Indicates the fields that should be expanded when marshaling a DTO.
 *
 * @author Dan Noguerol
 */
public class ExpansionFields {
    private Stack<String> contextStack = new Stack<>();
    private String contextPrefix = null;
    private List<String> expansionFields;

    /**
     * Constructor.
     *
     * @param expansions a comma-separated list of expansion fields
     */
    public ExpansionFields(String expansions) {
        if (expansions != null) {
            expansionFields = new ArrayList(Arrays.asList(StringUtils.split(expansions, ',')));
        }
    }

    public ExpansionFields(String context, String expansions) {
        this(expansions);
        pushContext(context);
    }

    public void add(String field) {
        if (expansionFields == null) {
            expansionFields = new ArrayList<>();
        }
        expansionFields.add(field);
    }

    public ExpansionFields pushContext(String context) {
        contextStack.push(context);
        buildContextPrefix();
        return this;
    }

    public void popContext() {
        if (!contextStack.empty()) {
            contextStack.pop();
            buildContextPrefix();
        }
    }

    protected void buildContextPrefix() {
        StringBuilder sb = new StringBuilder();
        int stackSize = contextStack.size();
        for (int i=0; i < stackSize; i++) {
            sb.append(contextStack.elementAt(i));
            if (i < stackSize - 1) {
                sb.append('.');
            }
        }
        contextPrefix = sb.length() > 0 ? sb.toString() : null;
    }

    /**
     * Indicates whether an expansion field is present.
     *
     * @param fieldName the name of the field to check
     *
     * @return a boolean
     */
    public boolean has(String fieldName) {
        if (contextPrefix != null) {
            fieldName = contextPrefix + "." + fieldName;
        }

        if (expansionFields != null && fieldName != null) {
            for (String field : expansionFields) {
                if (field.substring(0, Math.min(field.length(), fieldName.length())).equals(fieldName) && (field.length() == fieldName.length() || (field.length() > fieldName.length() && field.charAt(fieldName.length()) == '.'))) {
                    return true;
                }
            }
        }

        return false;
    }
}