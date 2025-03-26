package com.eup.fms.server.lib.annual_performance;

import static com.eup.fms.dao.table.object.db.cassandra.eup_statis.car_simple_statis.SimpleStatisType.FREEWAY_CHARGE_DATA;
import static com.eup.fms.dao.table.object.db.cassandra.eup_statis.car_simple_statis.SimpleStatisType.IDLING;
import static com.eup.fms.dao.table.object.db.cassandra.eup_statis.car_simple_statis.SimpleStatisType.MILEAGE;
import static com.eup.fms.dao.table.object.db.cassandra.eup_statis.car_simple_statis.SimpleStatisType.OVER_SPEED;

import com.eup.fms.dao.table.nosql.eup_statis.EupStatisFactory;
import com.eup.fms.dao.table.object.db.cassandra.eup_statis.car_simple_statis.SimpleStatisType;
import com.eup.fms.dao.table.object.db.cassandra.eup_statis.tb_car_simple_statis;
import com.eup.fms.dao.table.object.db.cassandra.eup_statis.tb_car_simple_statis.StatisDate;
import com.eup.fms.server.lib.annual_performance.bo.AnnualPerformanceBo;
import com.eup.fms.server.lib.annual_performance.bo.AnnualPerformanceStatistic;
import com.eup.fms.server.lib.golbal_value.car.GlobalCarDataByTeam;
import com.eup.fms.server.lib.golbal_value.object.SimpleCarData;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * 用于计算和提供车辆年度性能统计数据的服务类。
 * 该服务从多个数据源聚合数据，并提供包括里程、怠速时间、超速事件和
 * 高速公路收费数据在内的综合性能指标。
 */
@Service
public class AnnualPerformanceService {

    /**
     * 获取指定团队在给定时间范围内的年度性能数据。
     * 该方法聚合指定团队中所有车辆在给定时间段内的性能数据。
     *
     * @param custId 客户ID
     * @param teamId 团队ID
     * @param startTime 报告期的开始时间
     * @param endTime 报告期的结束时间
     * @return 包含指定团队性能数据的AnnualPerformanceBo对象
     * @throws Exception 如果获取数据时发生错误
     */
    public AnnualPerformanceBo getAnnualPerformance(String custId, String teamId, Date startTime, Date endTime) throws Exception {
        AnnualPerformanceBo annualPerformanceBo = new AnnualPerformanceBo();
        // 获取指定团队的车辆数据列表
        List<SimpleCarData> carDataList = GlobalCarDataByTeam.getInstance().getCarDataList(teamId);
        // 提取唯一的车辆标识符
        List<String> unicodeList = carDataList.stream().map(SimpleCarData::getCarUnicode).collect(Collectors.toList());
        // 定义要收集的统计数据类型
        List<SimpleStatisType> reportTypeList = new ArrayList<>(Arrays.asList(MILEAGE, IDLING, OVER_SPEED, FREEWAY_CHARGE_DATA));
        // 用于按类型存储统计数据的Map
        Map<SimpleStatisType, List<tb_car_simple_statis>> simpleDataMap = new EnumMap<>(SimpleStatisType.class);
        
        // 为每种类型检索统计数据
        for (SimpleStatisType type : reportTypeList) {
            simpleDataMap.put(type, EupStatisFactory.getInstance().getSimpleStatisRepository().select(custId, unicodeList, startTime, endTime, type));
        }
        
        // 处理并构建性能数据
        for (Entry<SimpleStatisType, List<tb_car_simple_statis>> entry : simpleDataMap.entrySet()) {
            buildAnnualPerformanceBo(annualPerformanceBo, entry.getValue(), entry.getKey());
        }
        
        return annualPerformanceBo;
    }

    /**
     * 根据给定的类型，从tb_car_simple_statis对象列表构建AnnualPerformanceBo。
     * 该方法处理原始统计数据，并将其组织成月度性能指标。
     *
     * @param bo 要构建的AnnualPerformanceBo对象
     * @param statistic 用于构建的tb_car_simple_statis对象列表
     * @param type 要构建的统计数据类型
     */
    private void buildAnnualPerformanceBo(AnnualPerformanceBo bo, List<tb_car_simple_statis> statistic, SimpleStatisType type) {
        if (statistic == null || statistic.isEmpty()) {
            return;
        }
        
        List<AnnualPerformanceStatistic> annualPerformance = new ArrayList<>();
        // 用于按统计日期组织数据的Map
        Map<StatisDate, List<Double>> dataMap = new EnumMap<>(StatisDate.class);
        // 用于存储每月总值的数组
        double[] totalValue = new double[12];
        
        // 处理每辆车的统计数据
        for (tb_car_simple_statis carSimpleStatistic : statistic) {
            carSimpleStatistic.getDataMap().forEach((key, value) ->
                dataMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value));
        }
        
        // 按月聚合数据
        for (Entry<StatisDate, List<Double>> entry : dataMap.entrySet()) {
            String columnName = entry.getKey().getColumnName().toLowerCase();
            List<Double> values = entry.getValue();
            int monthIndex = getMonthIndex(columnName);
            if (monthIndex >= 0) {
                for (Double value : values) {
                    if (value != null) {
                        totalValue[monthIndex] += value;
                    }
                }
            }
        }
        
        // 创建月度性能统计数据
        for (int i = 0; i < 12; i++) {
            AnnualPerformanceStatistic performanceStatistic = new AnnualPerformanceStatistic();
            performanceStatistic.setMonth(i + 1);
            performanceStatistic.setValue(BigDecimal.valueOf(totalValue[i]).setScale(1, RoundingMode.HALF_UP).doubleValue());
            annualPerformance.add(performanceStatistic);
        }
        
        // 根据统计类型将结果存储在适当的字段中
        switch (type) {
            case MILEAGE:
                bo.setDistanceTravelled(annualPerformance);
                break;
            case IDLING:
                bo.setIdlingTimes(annualPerformance);
                break;
            case OVER_SPEED:
                bo.setSpeedingTimes(annualPerformance);
                break;
            case FREEWAY_CHARGE_DATA:
                bo.setHighwayBillings(annualPerformance);
                break;
            default:
                break;
        }
    }

    /**
     * 将月份字符串（如"jan"、"feb"等）转换为其对应的年份索引（0-11）。
     *
     * @param columnName 要转换的月份字符串
     * @return 月份在年份中的索引，如果字符串无效则返回-1
     */
    private static int getMonthIndex(String columnName) {
        if (columnName ==  null || columnName.length() < 3) {
            return -1;
        }
        switch (columnName.substring(0,3)) {
            case "jan": return 0;
            case "feb": return 1;
            case "mar": return 2;
            case "apr": return 3;
            case "may": return 4;
            case "jun": return 5;
            case "jul": return 6;
            case "aug": return 7;
            case "sep": return 8;
            case "oct": return 9;
            case "nov": return 10;
            case "dec": return 11;
            default: return -1;
        }
    }
}
