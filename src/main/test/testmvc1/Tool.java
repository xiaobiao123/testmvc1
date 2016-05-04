package testmvc1;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public final class Tool {
	
	
	public static boolean isEmpty(String str){
		return str==null || str.length()<=0;
	}
	
	public static String nullToEmpty(String str){
		return str==null?"":str;
	}
	
	public static String formatTime(Date date){
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	/**
	 * 发送报文的md5签名格式
	 * 
	 * @param version
	 *            版本号
	 * @param merid
	 *            商户ID
	 * @param trancode
	 *            交易代码
	 * @param tmp
	 *            xml格式的交易报文
	 * @param charset
	 *            编码
	 * @param password
	 *            约定的密码
	 * @param transtime
	 *            交易时间
	 * @return 加密后的字符串
	 */
	public static String sendMd5(String version, String merid, String trancode,
			String tmp, String charset, String password, String transtime) {
		String returnStr = md5(
				md5(
						"version=" + version + "&merId=" + merid + "&tranCode="
								+ trancode + "&reqData=" + tmp, charset)
						.toUpperCase()
						+ "SEPGARAYJTOR"
						+ md5(password + transtime, charset).toUpperCase(),
				charset).toUpperCase();
		return returnStr;

	}

	
	/**
	 * 返回报文的md5签名格式
	 * 
	 * @param retxml
	 *            xml格式的返回报文
	 * @param merid
	 *            商户ID
	 * @param orderid
	 *            订单id
	 * @param charset
	 *            编码方式
	 * @param password
	 *            约定的密码
	 * @param transtime
	 *            返回的交易时间
	 * @return
	 */
	public static String returnMd5(String retxml, String merid, String orderid,
			String charset, String password, String transtime) {

		String returnStr = md5(
				md5(
						"reqData=" + retxml + "&merId=" + merid + "&orderId="
								+ orderid, charset).toUpperCase()
						+ "SEPGARAYJTOR"
						+ md5(password + transtime, charset).toUpperCase(),
				charset).toUpperCase();
		return returnStr;
	}
	
	/**
	 * md5加密算法
	 * 
	 * @param str
	 *            待加密字符串
	 * @param charset
	 *            编码方式
	 * @return
	 */
	public static String md5(String str, String charset) {

		if (str == null) {
			return null;
		}

		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes(charset));
		} catch (NoSuchAlgorithmException e) {

			return str;
		} catch (UnsupportedEncodingException e) {
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}
	
	
	
	
	
	/**
	 * 报文返回时间yyyyMMddHHmmss 商户需验证该时间和自己服务器时间间隔前后不超过15分钟
	 * 
	 * @param time
	 *            报文返回时间
	 * @return
	 */
	public static boolean checkTranstime(String time) {
		// TODO Auto-generated method stub
		if (time == null || time.length() != 14) {
			return false;
		}

		long t = Long.parseLong(time.substring(0, 8)) * 10000
				+ Long.parseLong(time.substring(8, 10)) * 60
				+ Long.parseLong(time.substring(10, 12));
		String now = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		long t1 = Long.parseLong(now.substring(0, 8)) * 10000
				+ Long.parseLong(now.substring(8, 10)) * 60
				+ Long.parseLong(now.substring(10, 12));

		// System.out.println("----|-----------|"+t);
		// System.out.println("----|-----------|"+t1);

		if ((t - t1) > 15 || (t - t1) < -15)
			return false;

		return true;
	}
	
/*	public static List<NameValuePair> convertToList(Object bean){
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				pairs.add(new BasicNameValuePair(field.getName(), (String)field.get(bean)));
			} catch (Exception e) {
				return null;
			} 
		}
		
		return pairs;
	}
	
	public static <T> T xmlToBean(String xml, Class<T> clazz){
		SAXReader saxreader = new SAXReader();
		Document document;
		T bean = null;
		try {
			document = saxreader.read(new ByteArrayInputStream(xml.getBytes(Constants.CHARSET)));
			Element root = document.getRootElement();//根节点GYJANS
			
			bean = xmlToBean(root, clazz);
			
		} catch (Exception e) {
			return null;
		}
		
		return bean;
		
	}
	
	public static <T> T xmlToBean(Element root, Class<T> clazz){
		T bean = null;
		try {
			bean = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					
					Element node = root.element(field.getName());
					if(node!=null) {
						field.set(bean, node.getText());
					}else {
						Elements tag = field.getAnnotation(Elements.class);
						if(tag!=null) {
							field.set(bean, Tool.xmlToListBean(root.element(tag.rootName()), Tool.getGenericClass(field)));
						}
					}
				} catch (Exception e) {
					
				} 
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return bean;
		
	}
	
	public static <T> List<T> xmlToListBean(Element root, Class<T> clazz){
		if(root==null || clazz==null) return null;
		
		List<T> list = null;
		try {
			List<Element> elements = root.elements();
			if(elements!=null) {
				list = new ArrayList<T>();
				
				for (Element element : elements) {
					list.add(Tool.xmlToBean(element, clazz));
				}
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return list;
		
	}*/

	private static Class getGenericClass(Field field){
		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		if(pt==null) return null;
		
		Type[] types = pt.getActualTypeArguments();
		return types!=null?(Class)types[0]:null;
	}
}
