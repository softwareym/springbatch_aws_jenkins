package ym.batch.job.Corona.item;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
public class CoronaVo {
    private Long seq;
    private String countryName;
    private Integer newCase;
    private Integer totalCase;
    private Integer recovered;
    private Integer death;
    private Float percentage;
    private Integer newCcase;
    private Integer newFcase;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
}
