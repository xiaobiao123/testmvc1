package testmvc1;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.user.pojo.User;

public class TestCopy {
	public static void main(String[] args) throws Exception {

		User user = new User();
		
		user.setId(222);
		user.setNickname("ickname");
		user.setName("name");
		user.setFlag(true);
		user.setDate(new Date());
		User toUser = new User();
		
		toUser.setNickname("11123");
		copyProperties1(user,toUser);
		System.out.println(toUser);
		//PropertyUtils.copyProperties(toUser,user);
		//copyProperties(user,toUser);
	//	System.out.println(toUser.getNickname());
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public static void copyProperties1(Object from,Object to){
		Field[] fields = from.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if(!Tool.isEmpty(field.get(from)+"")){
					field.set(to, field.get(from));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} 
		}
		System.out.println(to);
	}
	
	
	/** 
     * 利用反射实现对象之间属性复制 
     * @param from 
     * @param to 
     */  
    public static void copyProperties(Object from, Object to) throws Exception {  
        copyPropertiesExclude(from, to, null);  
    }  
	
	 /** 
     * 复制对象属性 
     * @param from 
     * @param to 
     * @param excludsArray 排除属性列表 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public static void copyPropertiesExclude(Object from, Object to, String[] excludsArray) throws Exception {  
        List<String> excludesList = null;  
        if(excludsArray != null && excludsArray.length > 0) {  
            excludesList = Arrays.asList(excludsArray); //构造列表对象  
        }  
        Method[] fromMethods = from.getClass().getDeclaredMethods();  
        Method[] toMethods = to.getClass().getDeclaredMethods();  
        Method fromMethod = null, toMethod = null;  
        String fromMethodName = null, toMethodName = null;  
        for (int i = 0; i < fromMethods.length; i++) {  
            fromMethod = fromMethods[i];  
            fromMethodName = fromMethod.getName();  
            if (!fromMethodName.contains("get"))  
                continue;  
            //排除列表检测  
            if(excludesList != null && excludesList.contains(fromMethodName.substring(3).toLowerCase())) {  
                continue;  
            }  
            toMethodName = "set" + fromMethodName.substring(3);  
            toMethod = findMethodByName(toMethods, toMethodName);  
            if (toMethod == null)  
                continue;  
            Object value = fromMethod.invoke(from, new Object[0]);  
            if(value == null)  
                continue;  
            //集合类判空处理  
            if(value instanceof Collection) {  
                Collection newValue = (Collection)value;  
                if(newValue.size() <= 0)  
                    continue;  
            }  
            toMethod.invoke(to, new Object[] {value});  
        }  
    }  
	
    /** 
     * 从方法数组中获取指定名称的方法 
     *  
     * @param methods 
     * @param name 
     * @return 
     */  
    public static Method findMethodByName(Method[] methods, String name) {  
        for (int j = 0; j < methods.length; j++) {  
        	
            if (methods[j].getName().equals(name))  
            	
                return methods[j];  
        }  
        return null;  
    }  
	
}
