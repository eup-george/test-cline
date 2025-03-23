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

@Service
public class AnnualPerformanceService {


    /**
     * Retrieves an AnnualPerformanceBo object for the given teamId, startTime and endTime.
     *
     * @param custId the customer id
     * @param teamId the team id
     * @param startTime the start time
     * @param endTime the end time
     * @return an AnnualPerformanceBo object containing the performance data of the given team
     * @throws Exception if there is an error
     */
    public AnnualPerformanceBo getAnnualPerformance(String custId, String teamId, Date startTime, Date endTime) throws Exception {
        AnnualPerformanceBo annualPerformanceBo = new AnnualPerformanceBo();
        List<SimpleCarData> carDataList = GlobalCarDataByTeam.getInstance().getCarDataList(teamId);
        List<String> unicodeList = carDataList.stream().map(SimpleCarData::getCarUnicode).collect(Collectors.toList());
        List<SimpleStatisType> reportTypeList = new ArrayList<>(Arrays.asList(MILEAGE, IDLING, OVER_SPEED, FREEWAY_CHARGE_DATA));
        Map<SimpleStatisType, List<tb_car_simple_statis>> simpleDataMap = new EnumMap<>(SimpleStatisType.class);
        for (SimpleStatisType type : reportTypeList) {
            simpleDataMap.put(type, EupStatisFactory.getInstance().getSimpleStatisRepository().select(custId, unicodeList, startTime, endTime, type));
        }
        for (Entry<SimpleStatisType, List<tb_car_simple_statis>> entry : simpleDataMap.entrySet()) {
            buildAnnualPerformanceBo(annualPerformanceBo, entry.getValue(), entry.getKey());
        }
        return annualPerformanceBo;
    }

    /**
     * Builds an AnnualPerformanceBo from a list of tb_car_simple_statis objects based on the given type.
     * @param bo the AnnualPerformanceBo to build
     * @param statistic the list of tb_car_simple_statis objects to build from
     * @param type the type of statistic to build
     */
    private void buildAnnualPerformanceBo(AnnualPerformanceBo bo, List<tb_car_simple_statis> statistic, SimpleStatisType type) {
        if (statistic == null || statistic.isEmpty()) {
            return;
        }
        List<AnnualPerformanceStatistic> annualPerformance = new ArrayList<>();
        Map<StatisDate, List<Double>> dataMap = new EnumMap<>(StatisDate.class);
        double[] totalValue = new double[12];
        for (tb_car_simple_statis carSimpleStatistic : statistic) {
            carSimpleStatistic.getDataMap().forEach((key, value) ->
                dataMap.computeIfAbsent(key, k -> new ArrayList<>()).add(value));
        }
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
        for (int i = 0; i < 12; i++) {
            AnnualPerformanceStatistic performanceStatistic = new AnnualPerformanceStatistic();
            performanceStatistic.setMonth(i + 1);
            performanceStatistic.setValue(BigDecimal.valueOf(totalValue[i]).setScale(1, RoundingMode.HALF_UP).doubleValue());
            annualPerformance.add(performanceStatistic);
        }
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
     * Converts a month string (e.g. "jan", "feb", etc.) into its corresponding
     * index (0-11) in the year.
     *
     * @param columnName the month string to convert
     * @return the index of the month in the year, or -1 if the string is invalid
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
