package clean.code.design_patterns.requirements._2_transportation_problem;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FactorySource implements Source {

    private String name;
    private Integer supply;
}
