package reflect;/**
 * Created by Easong on 2017/11/3.
 */


import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射 测试类
 *
 * @author Easong
 * @create 2017-11-03 11:52
 **/
public class ReflectTest {

    @Test
    public void getMethodThroughObject(){
       User u = new User();
       u.setName("easong");
        Class<? extends User> uClass = u.getClass();
        // 获取所有字段名
        Field[] fields = uClass.getDeclaredFields();
        for (Field f : fields){
            if(!"serialVersionUID".equals(f.getName())){
                System.err.println("字段名称：" + f.getName());
                // 获取get方法
                try {
                    // 通过字段名获取get方法
                    PropertyDescriptor pd = new PropertyDescriptor(f.getName(), uClass);
                    Method readMethod = pd.getReadMethod();
                    if(readMethod.invoke(u) != null){
                        System.err.println("字段值：" + readMethod.invoke(u));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("此属性没有get方法");
                }
            }
        }

    }
}
