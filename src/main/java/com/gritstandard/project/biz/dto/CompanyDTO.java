package com.gritstandard.project.biz.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private String name;
    private String groupNumber;
}
