package com.daxia.generator.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtils {
	
	public static double format(Double num) {
		DecimalFormat format = new DecimalFormat("0.00");
		format.setDecimalSeparatorAlwaysShown(true);
		if (num == null) {
			num = 0d;
		}
		String str = format.format(num);
		/* BigDecimal big=new BigDecimal(num);
		    System.out.println(big);
		    System.out.println(big.setScale(100,2));*/
//		   return big.doubleValue(); 
		 return Double.parseDouble(str);
	}
	
	public static double round(double value, int scale, int roundingMode) {   
       BigDecimal bd = new BigDecimal(value + "");   
       bd = bd.setScale(scale, roundingMode);
       System.out.println("--------" + bd.doubleValue());
       double d = bd.doubleValue();   
       bd = null;   
       return d;   
   }   
	
	public static void main(String[] args) {
	    double a = 33332569d;
	    System.out.println(round(a, 10, BigDecimal.ROUND_HALF_UP));
	    System.out.println(format(a));
    }
}
