package com.eup.fms.server.lib.api.sync_setting;

import com.eup.fms.dao.table.object.db._enum.RfidLightColorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RfidLightTurnOnObject {

    @Schema(description = "unicode")
    private String unicode;
    @Schema(description = "led1 color")
    @JsonProperty("led1")
    private RfidLightColorType led1;
    @Schema(description = "led2 color")
    @JsonProperty("led2")
    private RfidLightColorType color;
    @Schema(description = "duration")
    private Integer duration = 10; // default 10 seconds

}
