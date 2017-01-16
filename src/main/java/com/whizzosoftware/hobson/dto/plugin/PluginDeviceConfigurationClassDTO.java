package com.whizzosoftware.hobson.dto.plugin;

import com.whizzosoftware.hobson.api.persist.TemplatedId;
import com.whizzosoftware.hobson.api.plugin.HobsonLocalPluginDescriptor;
import com.whizzosoftware.hobson.api.property.PropertyContainerClass;
import com.whizzosoftware.hobson.api.property.PropertyContainerClassType;
import com.whizzosoftware.hobson.dto.ThingDTO;
import com.whizzosoftware.hobson.dto.context.DTOBuildContext;
import com.whizzosoftware.hobson.dto.context.TemplatedIdBuildContext;
import com.whizzosoftware.hobson.dto.property.PropertyContainerClassDTO;
import com.whizzosoftware.hobson.json.JSONAttributes;
import org.json.JSONObject;

public class PluginDeviceConfigurationClassDTO extends ThingDTO {
    private PropertyContainerClassDTO configurationClass;

    PluginDeviceConfigurationClassDTO(TemplatedIdBuildContext ctx, TemplatedId id) {
        super(ctx, id);
    }

    @Override
    public String getMediaType() {
        return "application/json";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(JSONAttributes.CCLASS, configurationClass != null ? configurationClass.toJSON() : null);
        return json;
    }

    public static class Builder {
        private PluginDeviceConfigurationClassDTO dto;

        public Builder(TemplatedIdBuildContext ctx, TemplatedId id) {
            dto = new PluginDeviceConfigurationClassDTO(ctx, id);
        }

        public Builder(DTOBuildContext ctx, final HobsonLocalPluginDescriptor plugin, final String name, final PropertyContainerClass pcc, boolean showDetails) {
            dto = new PluginDeviceConfigurationClassDTO(ctx, ctx.getIdProvider().createPluginDeviceConfigurationClassId(plugin.getContext(), name));
            if (showDetails) {
                dto.setName(name);
                boolean ccDetails = (ctx.getExpansionFields() != null && ctx.getExpansionFields().has(JSONAttributes.CCLASS));
                dto.configurationClass = new PropertyContainerClassDTO.Builder(ctx, ctx.getIdProvider().createPropertyContainerClassId(pcc.getContext(), PropertyContainerClassType.PLUGIN_CONFIG), pcc, ccDetails).build();
            }
        }

        public PluginDeviceConfigurationClassDTO name(String name) {
            dto.setName(name);
            return dto;
        }

        public PluginDeviceConfigurationClassDTO configurationClass(PropertyContainerClassDTO configurationClass) {
            dto.configurationClass = configurationClass;
            return dto;
        }

        public PluginDeviceConfigurationClassDTO build() {
            return dto;
        }
    }
}
