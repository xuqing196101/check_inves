package poi;/**
 * Created by Easong on 2017/11/6.
 */

/**
 * 测试类
 *
 * @author Easong
 * @create 2017-11-06 14:14
 **/
public class Man{

    private String name;

    private Integer age;

    private String sex;

    private Float sal;

    public Man() {
    }

    public Man(String name, Integer age, String sex, Float sal) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.sal = sal;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Float getSal() {
        return sal;
    }

    public void setSal(Float sal) {
        this.sal = sal;
    }
}
