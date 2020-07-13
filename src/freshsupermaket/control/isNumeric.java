package freshsupermaket.control;

import java.util.regex.Pattern;

public class isNumeric {
    public static boolean isNumeric1(String str){
    //判断字符串是否为非负数
            String reg = "^[0-9]+(.[0-9]+)?$";
            return str.matches(reg);
    }



    public static boolean isNumeric(String str){
        //判断字符串是否为正整数
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
