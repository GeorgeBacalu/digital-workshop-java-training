package clean.code.design_patterns.requirements._2_transportation_problem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WarehouseSource implements Source {

    private String name;
    private Integer supply;
}
