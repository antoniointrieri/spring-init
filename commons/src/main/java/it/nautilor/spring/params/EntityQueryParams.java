package it.nautilor.spring.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EntityQueryParams {
    private String id;
    private Integer page = 0;
    private Integer pageSize = 10;
}
