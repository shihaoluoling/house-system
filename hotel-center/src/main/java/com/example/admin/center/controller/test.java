package com.example.admin.center.controller;

/**
 * @author shihao
 * @Title: test
 * @ProjectName Second-order-center
 * @Description:
 * @date Created in
 * @Version: $
 */

import com.alibaba.fastjson.JSON;
import com.example.admin.center.manual.model.Area;
import com.example.admin.center.manual.model.City;
import com.example.admin.center.manual.model.Province;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 周志伟
 * @projectname 项目名称:
 * @classname: test
 * @description:
 * @date 2019/7/16：14:21
 */
public class test {
    public static void main(String[] args) {
        try {
            //2018年11月中华人民共和国县以上行政区划代码网页
            Document doc = Jsoup.connect("http://www.mca.gov.cn/article/sj/xzqh/2019/201901-06/201902061009.html").maxBodySize(0).get();
            Elements elements = doc.getElementsByClass("xl7016597");
            List<String> stringList = elements.eachText();
            List<String> stringName = new ArrayList<String>();
            List<String> stringCode = new ArrayList<String>();
            for (int i = 0; i < stringList.size(); i++) {
                if (i % 2 == 0) {
                    //地区代码
                    stringCode.add(stringList.get(i));
                } else {
                    //地区名字
                    stringName.add(stringList.get(i));
                }
            }
            //正常情况 两个 list size 应该 一样
            System.out.println("stringName  size= " + stringName.size() + "   stringCode   size= " + stringCode.size());
            if (stringName.size() != stringCode.size()) {
                throw new RuntimeException("数据错误");
            }
            List<Province> provinceList = processData(stringName, stringCode);

            System.out.println(provinceList.size());
            System.out.println(JSON.toJSON(provinceList));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成省份列表数据
     *
     * @param stringName
     * @param stringCode
     * @return
     */

    private static List<Province> processData(List<String> stringName, List<String> stringCode) {
        List<Province> provinceList = new ArrayList<Province>();
        for (int i = 0; i < stringCode.size(); i++) {
            String provinceName = stringName.get(i);
            String provinceCode = stringCode.get(i);
            if (provinceCode.endsWith("0000")) {
                Province province = new Province();
                provinceList.add(province);
                province.setCode(provinceCode);
                province.setName(provinceName);
                List<City> cities = new ArrayList<City>();
                province.setCityList(cities);
                System.out.println(" INSERT INTO `china`(`Id`, `Name`, `Pid`) VALUES ("+provinceCode+", '"+provinceName+"', 0);");
                //香港，澳门，台湾，没有市级行政单位划分，城市 地区 和省份保持一致
                if (provinceName.contains("香港") || provinceName.contains("澳门") || provinceName.contains("台湾")) {
                    City city = new City();
                    List<Area> areas = new ArrayList<Area>();
                    city.setName(provinceName);
                    city.setCode(provinceCode);
                    city.setAreaList(areas);
                    cities.add(city);
                    Area area = new Area();
                    area.setName(provinceName);
                    area.setCode(provinceCode);
                    areas.add(area);
                    System.out.println("  INSERT INTO `china`(`Id`, `Name`, `Pid`) VALUES ("+provinceCode+", '"+provinceName+"', 0);");
                }
                //直辖市 城市和省份名称一样
                if (provinceName.contains("北京") || provinceName.contains("上海") || provinceName.contains("天津") || provinceName.contains("重庆")) {
                    City city = new City();
                    List<Area> areas = new ArrayList<Area>();
                    city.setName(provinceName);
                    city.setCode(provinceCode);
                    city.setAreaList(areas);
                    cities.add(city);
                    System.out.println(" INSERT INTO `china`(`Id`, `Name`, `Pid`) VALUES ("+provinceCode+", '"+provinceName+"', 0);");
                    //县区
                    for (int k = 0; k < stringCode.size(); k++) {
                        String areaName = stringName.get(k);
                        String areaCode = stringCode.get(k);
                        if (!provinceCode.equals(areaCode) && areaCode.startsWith(provinceCode.substring(0, 2))) {
                            Area area = new Area();
                            area.setName(areaName);
                            area.setCode(areaCode);
                            areas.add(area);
                            System.out.println(" INSERT INTO `china`(`Id`, `Name`, `Pid`) VALUES ("+areaCode+", '"+areaName+"', "+provinceCode+");");
                        }
                    }
                }
                for (int j = 0; j < stringCode.size(); j++) {
                    String cityName = stringName.get(j);
                    String cityCode = stringCode.get(j);
                    //遍历获取地级市
                    if (!cityCode.equals(provinceCode) && cityCode.startsWith(provinceCode.substring(0, 2)) && cityCode.endsWith("00")) {
                        City city = new City();
                        List<Area> areas = new ArrayList<Area>();
                        city.setName(cityName);
                        city.setCode(cityCode);
                        city.setAreaList(areas);
                        cities.add(city);
                        System.out.println("  INSERT INTO `china`(`Id`, `Name`, `Pid`) VALUES ("+cityCode+", '"+cityName+"', "+provinceCode+");");
                        //遍历获取县区
                        for (int k = 0; k < stringCode.size(); k++) {
                            String areaName = stringName.get(k);
                            String areaCode = stringCode.get(k);
                            if (!areaCode.equals(cityCode) && areaCode.startsWith(cityCode.substring(0, 4))) {
                                Area area = new Area();
                                area.setName(areaName);
                                area.setCode(areaCode);
                                areas.add(area);
                                System.out.println(" INSERT INTO `china`(`Id`, `Name`, `Pid`) VALUES ("+areaCode+", '"+areaName+"', "+cityCode+");");
                            }
                        }
                    }
                }
            }
        }
        return provinceList;
    }
}
