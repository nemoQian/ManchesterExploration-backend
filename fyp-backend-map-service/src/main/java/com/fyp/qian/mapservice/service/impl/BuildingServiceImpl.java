package com.fyp.qian.mapservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fyp.qian.mapservice.pojo.Building;
import com.fyp.qian.mapservice.pojo.BuildingResponse;
import com.fyp.qian.mapservice.service.BuildingService;
import com.fyp.qian.mapservice.mapper.BuildingMapper;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.Proj4jException;
import org.locationtech.proj4j.ProjCoordinate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Yihan Qian
* @description 针对表【building】的数据库操作Service实现
* @createDate 2024-05-27 16:49:27
*/
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building>
    implements BuildingService{

    @Override
    public List<BuildingResponse> findBuildingByName(String buildingName) {
        QueryWrapper<Building> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("LOWER(name) LIKE LOWER({0})", "%" + buildingName + "%");
        List<Building> buildings = this.list(queryWrapper);
        List<BuildingResponse> buildingResponses = new ArrayList<>();
        for(Building building : buildings){
            String way = building.getWay().toString();
            byte[] wkbBytes = hexStringToByteArray(way);

            WKBReader wkbReader = new WKBReader();

            try{
                Geometry geometry = wkbReader.read(wkbBytes);
                double latitude = geometry.getCentroid().getCoordinate().y;
                double longitude = geometry.getCentroid().getCoordinate().x;
                double[] axios = new double[]{longitude, latitude};
                BuildingResponse buildingResponse = new BuildingResponse();
                buildingResponse.setBuildingName(building.getName());
                buildingResponse.setLnglat(tran(axios));
                buildingResponses.add(buildingResponse);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return buildingResponses;
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

    private static double[] tran(double[] doubles) {
        double x = doubles[0];
        double y = doubles[1];
        String projDefinition = "+proj=merc +a=6378137 +b=6378137 +lat_ts=0.0 +lon_0=0.0 +x_0=0.0 +y_0=0 +k=1.0 +units=m +nadgrids=@null +wktext +no_defs";

        // 创建 CoordinateReferenceSystem 对象
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem projCRS = crsFactory.createFromParameters("EPSG:3857", projDefinition);

        // 投影坐标转换为经纬度坐标
        try {
            ProjCoordinate projCoordinate = new ProjCoordinate(x, y);
            ProjCoordinate lonLatCoordinate = new ProjCoordinate();
            projCRS.getProjection().inverseProject(projCoordinate, lonLatCoordinate);

            // 获取经纬度坐标
            x = lonLatCoordinate.x;
            y = lonLatCoordinate.y;

            // System.out.println("Center Coordinate: (" + latitude + ", " + longitude + ")");
        } catch (Proj4jException e) {
            e.printStackTrace();
        }
        return new double[]{x, y};
    }
}




