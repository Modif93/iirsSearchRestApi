package com.connector.iirsSearch.util;

public class UtilMappUtm {
    public static yxUtmVo idx_to_utm(int _y_digit_1, int _y_digit_0, int _x_digit_1, int _x_digit_0)
    {
        yxUtmVo yxUtm = new yxUtmVo();

        yxUtm.setY(3937209.5 - (_y_digit_1 * 256 + _y_digit_0) * 10);
        yxUtm.setX(329378.72 + (_x_digit_1 * 256 + _x_digit_0) * 10);

        return yxUtm;
    }
}