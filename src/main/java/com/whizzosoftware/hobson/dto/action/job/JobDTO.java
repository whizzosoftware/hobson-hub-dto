/*
 *******************************************************************************
 * Copyright (c) 2017 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.dto.action.job;

import com.whizzosoftware.hobson.api.action.job.JobInfo;
import com.whizzosoftware.hobson.api.action.job.JobStatus;
import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.dto.MediaTypes;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JobDTO extends ThingDTO {
    private String status;
    private List<String> messages;

    private JobDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
    }

    private JobDTO(JSONObject json) {
        status = json.getString(JSONAttributes.STATUS);
        if (json.has(JSONAttributes.MESSAGES)) {
            JSONArray a = json.getJSONArray(JSONAttributes.MESSAGES);
            if (a.length() > 0) {
                messages = new ArrayList<>();
                for (int i = 0; i < a.length(); i++) {
                    messages.add(a.getString(i));
                }
            }
        }
    }

    @Override
    public String getMediaType() {
        return MediaTypes.JOB;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.STATUS, status);
        JSONArray a = new JSONArray();
        for (String m : messages) {
            a.put(m);
        }
        json.put(JSONAttributes.MESSAGES, a);
        return json;
    }

    static public class Builder {
        private JobDTO dto;

        public Builder(JSONObject json) {
            dto = new JobDTO(json);
        }

        public Builder(final DTOBuildContext bctx, String jobId, JobInfo job, boolean showDetails) {
            dto = new JobDTO(bctx, bctx.getIdProvider().createJobId(null, jobId));
            if (showDetails) {
                status(job.getStatus());
                messages(job.getStatusMessages());
            }
        }

        public Builder status(JobStatus status) {
            dto.status = status.toString();
            return this;
        }

        public Builder messages(List<String> messages) {
            dto.messages = messages;
            return this;
        }

        public JobDTO build() {
            return dto;
        }
    }
}
