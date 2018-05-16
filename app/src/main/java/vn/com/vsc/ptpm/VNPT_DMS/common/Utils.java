package vn.com.vsc.ptpm.VNPT_DMS.common;

import java.util.Arrays;
import java.util.List;

/**
 * Created by VietNH on 3/1/2017.
 */

public class Utils {

    private static List<String> listMaDauSoDT = Arrays.asList("09","01","020","0210","0211","0218","0219","022","0230","0231"
            ,"0240","0241","025","026","027","0280","0281","029","030","031","0320","0321","033","0350","0351"
            ,"036","037","038","039","04","0500","0501","0510","0511","052","053","054","055"
            ,"056","057","058","059","060","061","062","063","064","0650","0651","066","067","068","070","0710","0711",
            "072","073","074","075","076","077","0780","0781","079","08");

    public static boolean checkDauSoDT(String soDT) {
        for (String prefix : listMaDauSoDT) {
            if (soDT.trim().toUpperCase().startsWith(prefix.trim().toUpperCase())) {
                return true;
            } else {
                continue;
            }
        }
        return false;
    }
}
