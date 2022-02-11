package com.hefu.common.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class contains the common utilities methods
 */
public final class Utils {
    
	/**
	 * default private constructor
	 * 
	 * @Date : 2014-3-28
	 */
	private Utils() {

	}
	
	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	/**
	 * Check if the input str is null or ""
	 * 
	 * @Date : 2014-3-21
	 * @param str
	 *            - string
	 * @return true if str is null or empty;otherwise, false
	 */
	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * Check if the input str is not null and ""
	 * 
	 * @Date : 2014-3-21
	 * @param str
	 *            - string
	 * @return true if str is not null and empty
	 */
	public static boolean notEmpty(String str) {
		return str != null && !str.equals("null") && str.trim().length() > 0;
	}
    /**
     * Check if the input long id is not null and 0
     * @Date        :      2014-7-19
     * @param id
     * @return
     */
	public static boolean notNullAndZero(Long id) {
		return id != null && id != 0;
	}
	/**
     * Check if the input Bigdecimal id is not null and 0
     * @Date        :      2019-10-25
     * @param bigDecimal
     * @return
     */
    public static boolean notNullAndZero(BigDecimal bigDecimal) {
        return bigDecimal != null && bigDecimal.compareTo(new BigDecimal(0)) != 0;
    }
	/**
	 * Check if the input integer id is not null and 0		
	 * @Date        :      2014-7-19
	 * @param id
	 * @return
	 */
	public static boolean notNullAndZero(Integer id) {
		return id != null && id != 0;
	}
	
	/**
	 * Check if the input double value is not null and 0		
	 * @Date        :      2014-7-19
	 * @param value
	 * @return
	 */
	public static boolean notNullAndZero(Double value) {
		return value != null && value != 0;
	}
	
	public static boolean notNullAndNegative(Double value) {
		return value != null && value >= 0;
	}
	
	/**
	 * check if the value is null and negative
	 * @Date        :      2014-11-8
	 * @param value - value
	 * @return ture/false
	 */
	public static boolean notNullAndNegative(Integer value){
	    return value != null && value >= 0;
	}
	
	/**
	 * check if the value is null and negative
	 * @Date        :      2014-11-8
	 * @param value - value
	 * @return true/false
	 */
	public static boolean notNullAndNegative(Long value){
	    return value != null && value >= 0;
	}
	/**
	 * Formats the date input with the given format
	 * 
	 * @Date : 2014-1-25
	 * @param format
	 *            - date format strign
	 * @param date
	 *            - instance of java.util.Date
	 * @return string representation of date
	 */
	public static String formatDate(String format, Date date) {
		if (isNullOrEmpty(format) || date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * Transfer \\ to / in path
	 * 
	 * @Date : 2014-3-9
	 * @param path
	 *            - uri path string
	 * @return String
	 */
	public static String transferPath(String path) {
		if (path == null) {
			return null;
		}
		path = path.replace("\\", "/");
		return path;
	}

	/**
	 * 格式化string
	 * 
	 * @Date : 2014-6-2
	 * @param str
	 * @return
	 */
	public static String removeNull(String str) {
		if (str == null)
			str = "";
		if (str.equals("null"))
			str = "";
		return str.trim();
	}
	
	public static String removeNull(Double value) {
		String str = value + "";
		if (value == null)
			str = "";
		return str.trim();
	}
	
	public static String removeNull(Long value) {
		String str = value + "";
		if (value == null)
			str = "";
		return str.trim();
	}
	
	public static String removeNull(Integer value) {
		String str = value + "";
		if (value == null)
			str = "";
		return str.trim();
	}
	
	public static String removeNull(BigDecimal value) {
		String str = value + "";
		if (value == null)
			str = "";
		return str.trim();
	}

	/**
	 * Parse the date with the given date string and date format.
	 * 
	 * @Date        :      2014-6-9
	 * @param dateStr a string of date
	 * @param format a string of date format
	 * @return an instance of Date after parsed
	 * @throws ParseException 
	 */
	public static Date parseDate(String dateStr, String format) throws ParseException {
        if (isNullOrEmpty(format) || isNullOrEmpty(dateStr)) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        
        return dateFormat.parse(dateStr.trim());
	}
	
	/**
	 * 把时间格式String转成Timestamp
	 * @Date        :      2019-8-15
	 * @param dateStr a string of date
	 * @return Timestamp
	 */
	public static Timestamp parseTimestamp(String dateStr){
        if (isNullOrEmpty(dateStr) || isNullOrEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat simf = new SimpleDateFormat("yyyy-MM-dd");
        try {
        	long l = simf.parse(dateStr).getTime();
        	return new Timestamp(l);
		} catch (ParseException e) {
			e.printStackTrace();
		}      
        return null;
	}
	
	/**
	 * 得到本周的星期一的日期
	 * @Date        :      2014-6-27
	 * @return
	 */
	public static String getMondayOfThisWeek(){
	    Calendar c = Calendar.getInstance();
	    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;
	    if(dayOfWeek == 0){
	        dayOfWeek = 7;
	    }
	    c.add(Calendar.DATE, -dayOfWeek + 1);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    return sdf.format(c.getTime());
	}
	
	/**
     * 得到本周之前的最后一个的星期日的日期
     * @Date        :      2014-6-27
     * @return
     */
    public static String getLastSundayBeforeThisWeek(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek == 0){
            dayOfWeek = 7;
        }
        c.add(Calendar.DATE, -dayOfWeek);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }
	
	/**
	 * 
	 * @Date        :      2014-7-6
	 * @param currentContactCodeSeq
	 * @return
	 */
//	public static String getNextContactCodeSeq(String dealerOrgCode,Integer contactCodeSeq){
//	    
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		
//		contactCodeSeq = contactCodeSeq + 1;
//
//        return dealerOrgCode + year + StringUtils.leftPad(contactCodeSeq + "", 6, "0");
//   
//	}
	
	/**
	 * 
	 * @Date        :      2014-7-13
	 * @param ids
	 * @return
	 */
	public static String getSplitStr(List<Long> ids){
	    StringBuilder sb = new StringBuilder();
	    if(ids != null && ids.size() >0){
	        for(int i = 0;i<ids.size();i++){
	            Long id = ids.get(i);
	            if(i == 0){
	                sb.append(id);
	            }else{
	                sb.append(","+id);
	            }
	        }
	    }
	    return sb.toString();
	}
/**
 * 将字符串数组组合成以指定字符分隔的字符串，不指定字符时以逗号分隔
 * @Date        :      2014-7-15
 * @param objects
 * @param splitChar
 * @return
 */
	public static String getSplitString(String[] objects,String splitChar){
	    
	    if(Utils.isNullOrEmpty(splitChar)){
	        splitChar="," ;
	    }
	    
        StringBuilder sb = new StringBuilder();
        if(objects != null && objects.length >0){
            for(int i = 0;i<objects.length;i++){
                String id = objects[i];
                if(i == 0){
                    sb.append(id);
                }else{
                    sb.append(splitChar+id);
                }
            }
        }
        return sb.toString();
	}
	/**
	 * 将Long数组组合成以指定字符分隔的字符串，不指定字符时以逗号分隔
	 * @Date        :      2014-7-15
	 * @param objects
	 * @param splitChar
	 * @return
	 */
	   public static String getSplitString(Long[] objects,String splitChar){
	        
	        if(Utils.isNullOrEmpty(splitChar)){
	            splitChar="," ;
	        }
	        
	        StringBuilder sb = new StringBuilder();
	        if(objects != null && objects.length >0){
	            for(int i = 0;i<objects.length;i++){
	                Long id = objects[i];
	                if(i == 0){
	                    sb.append(id);
	                }else{
	                    sb.append(splitChar+id);
	                }
	            }
	        }
	        return sb.toString();
	    }
	   /**
	    * 
	    * @Date        :      2014-7-22
	    * @param strs
	    * @param splitChar
	    * @return
	    */
       public static List<String> getSplitList(String strs,String splitChar){
           
           if(Utils.isNullOrEmpty(splitChar)){
               splitChar="," ;
           }
           
           List<String> list=new ArrayList<String>();
           for(String str:strs.split(splitChar)){
               list.add(str);
           }
           
           return list;
       }
       
       public static Date getDateStrByDateAndTime(String date, String time) {
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String str=date;
           if (time!=null&&!"".equals(time)) {
               str=str + " " + time + ":00";
           }else{
        	   return new Date();
           }
           try {
               return dateFormat.parse(str);
           }catch (ParseException e) {
               e.printStackTrace();
               return null;
           }
      }
       /**
        * get database column date value
        * @Date        :      2014-11-3
        * @param obj - column object
        * @return - Date
        */
       public static Date getDate(Object obj){
           if(null == obj){
               return null;
           }
           return (Date)obj;
       }
       
       /**
        * get database column long value
        * @Date        :      2014-11-3
        * @param obj - column object
        * @return - Long
        */
       public static Long getLong(Object obj){
           if(null == obj){
               return null;
           }
           return ((BigDecimal)obj).longValue();
       }

       /**
        * get database column int value
        * @Date        :      2014-11-3
        * @param obj - column object
        * @return - Integer
        */
       public static Integer getInt(Object obj){
           if(null == obj){
               return null;
           }
           return Integer.parseInt(obj.toString());
          // return ((BigDecimal)obj).intValue();
       }
       
       /**
        * get database column string value
        * @Date        :      2014-11-3
        * @param obj - column object
        * @return - String
        */
       public static String getString(Object obj){
           if(null == obj){
               return "";
           }
           return String.valueOf(obj);
       }
       
    
  
    /**
     * 
     * @Date        :      Dec 9, 2014
     * @param s    字符串
     * @param Length   验证长度
     * @param addmunber 遇到非英文字符   需要累加书
     * @return
     */
      public static boolean widthCheck(String s,int Length ,int addmunber){
    	int  count =StringLength(s,addmunber);
    	if(count<=Length){
    		return true;
    	}else{
    		return false;
    	}
    }
    /**
     *  
     * @Date        :      Dec 9, 2014
     * @param s
     * @param addmunber
     * @return
     */
    public static int StringLength(String s,int addmunber){
    	int count =0;
    	int add =addmunber;
    	if(s==null || s.equals("")){
        	count=0;		
    	}else{
    		 char[] ch = s.toCharArray();
    		 for(int i =0 ;i< ch.length;i++){
    			if((ch[i]>0x0001&&ch[i]<0x007e)||(0xff60<=ch[i]&&ch[i]<=0xff9f)){
    				count++;
    			}else{
    				count+=add;
    			}
    		}
    	}
		 return  count;
     }
    /**
     *   
     * @Date        :      Dec 9, 2014
     * @param s
     * @return
     */
    public static boolean  decimal_16_4Validato(String s) {
    	Pattern pattern = Pattern.compile("^\\d{0,12}(\\.\\d{1,4})?$");
    	if(pattern.matcher(s).matches()){
    		return  true;
    	}
    	return  false;	     	  
    }
    
    /**
     * Replace event term 
     * @Date        :      2014-12-14
     * @return
     */
    public static String replaceForEventTerm(String eventTerm){
        String resultStr = eventTerm;
        if(notEmpty(resultStr)){
            resultStr = resultStr.replaceAll("\r\n", "<br/>");
            resultStr = resultStr.replaceAll("\n", "<br/>");
            resultStr = resultStr.replaceAll("\\r\\n", "<br/>");
            resultStr = resultStr.replaceAll("\\n", "<br/>");
            resultStr = resultStr.replaceAll("\\\\r\\\\n", "<br/>");
            resultStr = resultStr.replaceAll("\\\\n", "<br/>");
            resultStr = resultStr.replaceAll(" ", "&nbsp;");
        }
        return resultStr;
    }
    
    /**
     * 判断srcString字符串（‘,’分隔符分隔）中是否包含key字符串
     * @Date        :      Mar 23, 2012
     * @param key
     * @param srcString
     * @return -boolean
     */
    public static boolean containsKey(String key,String srcString){
    	if(Utils.notEmpty(key)&&Utils.notEmpty(srcString)){
    		String[] temp = srcString.split(",");
    		for(int index = 0;index < temp.length;index++){
    			if(key.equals(temp[index]))return true;
    		}
    	}
    	return false;
    }
 
    /**
     * 不等于 0，空，-1
     * @Date        :      2012-4-16
     * @param str
     * @return
     */
   public static boolean notNullAndZeroAndNegativeOne(String str){
       return Utils.notEmpty(str) && !"0".equals(str) && !"-1".equals(str);
   }
   
   /**
    * 返回长度为【strLength】的随机数，在前面补0
    * @param strLength
    * @return
    */
   public static String getFixLenthString(int strLength) {
		Random rm = new Random();
		Double d = (1 + rm.nextDouble()) * Math.pow(10, strLength);
		long l = d.longValue();
		return String.valueOf(l).substring(1, strLength + 1);
	}
	
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0;i < s.length();i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			}else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				}catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0;j < b.length;j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	public static String format(double value){
		DecimalFormat format = new DecimalFormat("0.##");
		return format.format(value);
	}
	
	public static float parseFloat(String value){
		return Float.parseFloat(value);
	}
	
	public static String toBinaryString(int intdata){
		String str = Integer.toBinaryString( intdata );
		int length = str.length();
		if( length < 8 ){
			for( int i = 0;i < (8-length);i++ ){
				str = "0" + str;
			}
		}
		return str;
	}
	
	public static String binaryString(int intdata, int startPos, int endPos){
		String str = toBinaryString(intdata);
		return str.substring(startPos, endPos);
	}
	
	/**
	 * 获取高位或者低位字节
	 * @param value
	 * @param lowHigh 1：高位  0：低位
	 * @return
	 */
	public static String getHighOrLowByte(String value, int lowHigh){
		if(lowHigh==1){
			return String.valueOf(Integer.parseInt(value) >> 8);
		}
		return String.valueOf(Integer.parseInt(value) & 0x00FF);
	}
	
	public static String removeBraces(String content){
		return content.replaceAll("\\{", "").replaceAll("\\}", "");
	}

//	public static String getFileMD5(File file) {
//		if (!file.isFile()) {
//	        return null;
//	    }
//	    MessageDigest digest = null;
//	    FileInputStream in = null;
//	    byte buffer[] = new byte[8192];
//	    int len;
//	    try {
//	        digest = MessageDigest.getInstance("MD5");
//	        in = new FileInputStream(file);
//	        while ((len = in.read(buffer)) != -1) {
//	            digest.update(buffer, 0, len);
//	        }
//	        BigInteger bigInt = new BigInteger(1, digest.digest());
//	        String md5 = bigInt.toString(16);
//	        return md5.length() < 32 ? StringUtils.leftPad(md5, 32, "0") : md5;
//	    }catch (Exception e) {
//	        e.printStackTrace();
//	        return null;
//	    }finally {
//	        try {
//	            in.close();
//	        }catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}
	
//	public static String getFileContentMD5(String dealtContext) {
//		if (isNullOrEmpty(dealtContext)) {
//	        return null;
//	    }
//	    MessageDigest digest = null;
//	    Reader reader = null;
//	    char buffer[] = new char[8192];
//	    int len;
//	    try {
//	        digest = MessageDigest.getInstance("MD5");
//	        reader = new StringReader(dealtContext);
//	        while ((len = reader.read(buffer)) != -1) {
//	        	String temp = new String(buffer);
//	            digest.update(temp.getBytes(), 0, len);
//	        }
//	        BigInteger bigInt = new BigInteger(1, digest.digest());
//	        String md5 = bigInt.toString(16);
//	        return md5.length() < 32 ? StringUtils.leftPad(md5, 32, "0") : md5;
//	    }catch (Exception e) {
//	        e.printStackTrace();
//	        return null;
//	    }finally {
//	        try {
//	        	reader.close();
//	        }catch (Exception e) {
//	            e.printStackTrace();
//	        }
//	    }
//	}
	
	public static boolean isIP(String addr) {
        if(addr == null || addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        return mat.find();
    }
	
	public static int ping(String ip) {
		int maxTime = Integer.MIN_VALUE;
		try {
			Process p = Runtime.getRuntime().exec("ping " + ip + " -n 1");
			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr);
			String line;
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine()) != null){
				sb.append(line);
			}
			p.destroy();
			reader.close();
			isr.close();
			is.close();
			
			String[] mes = sb.toString().split(" +");
			String timeString;
			for (int i = 0; i < mes.length; i++) {
				if (mes[i].startsWith("TTL=")) {
					timeString = mes[i-1].replaceAll(mes[i-1].split("(?:(<*+\\d+ms))")[0], "");
					Pattern pat = Pattern.compile("\\D+");
					Matcher m = pat.matcher(timeString);
					int time = Integer.valueOf(m.replaceAll("").trim());
					if (time > maxTime) {
						maxTime = time;
					}
				}
			}
		} catch (IOException e) { }
		if (maxTime == Integer.MIN_VALUE) {
			return Integer.MAX_VALUE;
		}else {
			return maxTime;
		}
	}
	
	/**
	 * <p>Description: 时间格式验证</p>
	 * @author Mr.Cheng
	 * @date 2019年4月18日
	 * @param str
	 * @return
	 */
	public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }
	
	/**
	 * <p>Description: xml格式字符串解析</p>
	 * @author lixq
	 * @param featureId 
	 * @date 2019年5月9日
	 * @param strXML
	 * @return
	 */
	public static boolean domFromString(String strXML, long featureId)
	{
    	//List<String> list = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();		
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            StringReader sr = new StringReader(strXML);
            InputSource is = new InputSource(sr);
			Document doc = builder.parse(is);    
        	doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("feature");
			for(int i = 0 ; i<nList.getLength();i++){
			    Node node = nList.item(i);			     
			    Element ele = (Element)node;
			    
			    if(node.getNodeType() == Element.ELEMENT_NODE && String.valueOf(featureId).equals(ele.getAttribute("id"))){			     
//			    	System.out.println("feature id: "+ ele.getAttribute("id"));			     
//				    System.out.println("feature usable: "+ ele.getAttribute("usable"));			     
//				    System.out.println("feature name: "+ ele.getAttribute("name"));			
//				    list.add(ele.getAttribute("usable"));
//				    System.out.println("title name: "+ ele.getElementsByTagName("title").item(0).getTextContent());			     
//				    System.out.println("author name: "+ele.getElementsByTagName("author").item(0).getTextContent());			     
//				    System.out.println("year :"       +ele.getElementsByTagName("year").item(0).getTextContent());			     
//				    System.out.println("price : "     +ele.getElementsByTagName("price").item(0).getTextContent());			    }
			    	return true;
			    }
			}
		}catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch(Exception ex) {
        	ex.printStackTrace();
		}
		return false;		
	}
	
//	public static RobotForm getRobotConfig(String robotId) {
//		return robotMap.getOrDefault(robotId, robotMap.get("default"));
//	}
//	
//	public static RackTypeConfig getRackTypeConfig(String rackKind) {
//		return rackConfigMap.get(rackKind);
//	}
//	
//	public static JSONArray getAssetLightAvailableRegions(String key) {
//		return assetLightAvailableRegionMap.get(key);
//	}

    public static void main(String[] args) throws Exception {
//    	Hasp hasp = HaspDogAspect.getHasp();
//    	hasp.login(Constants.VENDORCODE);
//        int status = hasp.getLastError();
//        if (HaspStatus.HASP_STATUS_OK != status) {
//        	System.out.println("登录失败，" + status);
//            System.exit(0);
//        }
////		String haspCount = Utils.readHaspContent(0, 5).trim();      //第一个5位存放可管理总量    没有数据的时候默认是个 "."
////        String useCount = Utils.readHaspContent(5, 5).trim();      //第二个5位存放已经使用总量
////        System.out.println(haspCount);
////        System.out.println(useCount);
//        Utils.writeHaspContent(20, "1");
//        Utils.writeHaspContent(21, "3");
//        String useCount = Utils.readHaspContent(20, 1).trim();
//        System.out.println(useCount);
//        useCount = Utils.readHaspContent(21, 2).trim();
//        System.out.println(useCount);
        
	}
    
}