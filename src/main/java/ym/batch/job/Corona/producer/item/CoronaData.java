package ym.batch.job.Corona.producer.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import ym.batch.job.common.json.CustomLocalDateSerializer;

import java.time.LocalDate;

@Getter
@Setter
public class CoronaData {
    @JsonProperty("new_case")
    private int newCase;

    @JsonProperty("station_name")
    private String stationName;

    private String email;

    @JsonProperty("create_date")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate createDate;
}
