package reflect;/**
 * Created by Easong on 2017/11/3.
 */

import java.io.Serializable;

/**
 * 测试User类
 *
 * @author Easong
 * @create 2017-11-03 11:53
 **/
public class User implements Serializable{

    private static final long serialVersionUID = 2258924863925352931L;

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
