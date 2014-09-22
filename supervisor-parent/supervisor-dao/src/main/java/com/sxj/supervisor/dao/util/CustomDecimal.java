package com.sxj.supervisor.dao.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CustomDecimal
{
    private static final Character[] HIGHER_DIGITS = { 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    
    private static final Character ZERO = HIGHER_DIGITS[0];
    
    public static final long LOWER = 100000;
    
    public static String getDecimalString(int highdigits, BigDecimal value)
    {
        long steps = value.divide(BigDecimal.valueOf(LOWER)).longValue();
        long thisValue = value.subtract(BigDecimal.valueOf(steps)
                .multiply(BigDecimal.valueOf(LOWER))).longValue();
        DecimalFormat formatter = getFormatter();
        String lower = formatter.format(thisValue);
        StringBuffer sb = new StringBuffer();
        int log = log(steps, HIGHER_DIGITS.length);
        if (log > highdigits)
            throw new RuntimeException("Out of Number range");
        long temp = steps;
        for (int i = log; i >= 0; i--)
        {
            long a = ((long) Math.pow(HIGHER_DIGITS.length, i));
            int x = (int) (temp / a);
            sb.append((char) (ZERO + x));
            temp = temp - x * a;
        }
        if (sb.length() < highdigits)
        {
            String prefix = "";
            for (int i = 0; i < highdigits - sb.length(); i++)
                prefix += ZERO;
            sb.insert(0, prefix);
        }
        
        sb.append(lower);
        return sb.toString();
    }
    
    public static int log(long value, int indices)
    {
        int logN = 1;
        while (true)
        {
            double pow = Math.pow(indices, logN);
            if (pow <= value)
                logN++;
            else
                break;
        }
        return logN - 1;
    }
    
    private static DecimalFormat getFormatter()
    {
        String valueOf = String.valueOf(LOWER);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < valueOf.length() - 1; i++)
        {
            sb.append("0");
        }
        return new DecimalFormat(sb.toString());
    }
    
    public static void main(String... args)
    {
        System.out.println(CustomDecimal.getDecimalString(3,
                BigDecimal.valueOf(1757599999)));
    }
}
