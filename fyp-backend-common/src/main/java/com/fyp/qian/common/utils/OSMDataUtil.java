package com.fyp.qian.common.utils;

import com.fyp.qian.model.pojo.response.PlaceResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.Proj4jException;
import org.locationtech.proj4j.ProjCoordinate;

import java.util.Set;

public class OSMDataUtil {

    public static double[] generateLatLon(String way) {
        byte[] wkbBytes = hexStringToByteArray(way);

        WKBReader wkbReader = new WKBReader();

        try{
            Geometry geometry = wkbReader.read(wkbBytes);
            double latitude = geometry.getCentroid().getCoordinate().y;
            double longitude = geometry.getCentroid().getCoordinate().x;
            double[] axios = new double[]{longitude, latitude};
            return generateLatLon(axios);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateDescription(Object tags){
        return "";
    }

    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    private static double[] generateLatLon(double[] doubles) {
        double longitude = doubles[0];
        double latitude = doubles[1];
        String projDefinition = "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext +no_defs";

        // 创建 CoordinateReferenceSystem 对象
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem projCRS = crsFactory.createFromParameters("EPSG:3857", projDefinition);

        // 投影坐标转换为经纬度坐标
        try {
            ProjCoordinate projCoordinate = new ProjCoordinate(longitude, latitude);
            ProjCoordinate lonLatCoordinate = new ProjCoordinate();
            projCRS.getProjection().inverseProject(projCoordinate, lonLatCoordinate);

            // 获取经纬度坐标
            longitude = lonLatCoordinate.x;
            latitude = lonLatCoordinate.y;

            // System.out.println("Center Coordinate: (" + latitude + ", " + longitude + ")");
        } catch (Proj4jException e) {
            e.printStackTrace();
        }
        return new double[]{longitude, latitude};
    }
}
