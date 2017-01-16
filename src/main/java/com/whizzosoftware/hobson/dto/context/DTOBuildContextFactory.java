/*
 *******************************************************************************
 * Copyright (c) 2015 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.context;

import com.whizzosoftware.hobson.dto.ExpansionFields;

/**
 * An interface for classes that can create DTOBuildContext objects for use in DTO construction.
 *
 * @author Dan Noguerol
 */
public interface DTOBuildContextFactory {
    /**
     * Create a new DTOBuildContext with a given API root and set of expansion fields.
     *
     * @param requestDomain the domain associated with the original request
     * @param apiRoot the API root string
     * @param expansions a list of fields to expand when constructing the DTO graph
     *
     * @return a DTOBuildContext instance
     */
    DTOBuildContext createContext(String requestDomain, String apiRoot, ExpansionFields expansions);
    DTOBuildContext createContext(String apiRoot, ExpansionFields expansions);
}
