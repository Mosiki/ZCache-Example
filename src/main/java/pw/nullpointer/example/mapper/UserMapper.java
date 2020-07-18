package pw.nullpointer.example.mapper;

import org.apache.ibatis.annotations.*;
import pw.nullpointer.example.model.User;

/**
 * @author WeJan
 * @since 2020-07-18
 */
@Mapper
public interface UserMapper {

    @Update("CREATE TABLE t_user (id bigint NOT NULL , name varchar(128) DEFAULT NULL, age int NOT NULL,PRIMARY KEY (id) ) ")
    void createTable();

    @Update("DROP TABLE t_user")
    void dropTable();

    @Insert(value = "insert into t_user (id, name, age)values (#{id}, #{name}, #{age} )")
    int insert(User user);

    @Select("select * from t_user where id = #{id} ")
    User findById(@Param("id") Long id);

}
