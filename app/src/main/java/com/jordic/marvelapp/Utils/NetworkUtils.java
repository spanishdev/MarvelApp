package com.jordic.marvelapp.Utils;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by J on 05/02/2017.
 */

public class NetworkUtils {

    public static final String BASE_URL="http://gateway.marvel.com";

    /**
     * Generate the Hash using the variables required by the webservice
     * @param ts TimeStamp (in milliseconds)
     * @param privateKey Private Key
     * @param publicKey Public Key (or Api Key)
     * @return The MD5 Hash
     */
    public static String getHash(long ts, String privateKey, String publicKey) {

        String md5String="";
        try {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(ts).append(privateKey).append(publicKey);

            MessageDigest  md5 = MessageDigest.getInstance("MD5");
            byte[] md5sum = md5.digest(stringBuilder.toString().getBytes());
            md5String = String.format("%032X", new BigInteger(1, md5sum));

        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Log.e("NO ALGORITHM FOUND","MD5 DOES NOT EXIST");
        }
        return md5String.toLowerCase();
    }

}
