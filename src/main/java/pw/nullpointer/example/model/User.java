package pw.nullpointer.example.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author WeJan
 * @since 2020-07-18
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private Integer age;
}
