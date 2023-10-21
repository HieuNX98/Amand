package com.amand.ServiceImpl;

import com.amand.Utils.GetUtils;
import com.amand.constant.SystemConstant;
import com.amand.dto.PayDto;
import com.amand.form.PayForm;
import com.amand.service.IOrderService;
import com.amand.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements IReportService {

    @Autowired
    private IOrderService orderService;

    @Override
    public List<PayDto> getRevenueByForm(PayForm payForm) {
        List<PayDto> payDtos = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        List<String> times;
        if(SystemConstant.TODAY.equals(payForm.getSelect())) {
            String time = GetUtils.formatDateToString(calendar.getTime());
            payDtos = orderService.findAllByDay(time);
            if (CollectionUtils.isEmpty(payDtos)) {
                setTimeToPayDtos(payDtos, time);
            }
        } else if (SystemConstant.YESTERDAY.equals(payForm.getSelect())) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            String time = GetUtils.formatDateToString(calendar.getTime());
            payDtos = orderService.findAllByDay(time);
            if (CollectionUtils.isEmpty(payDtos)) {
                setTimeToPayDtos(payDtos, time);
            }
        } else if (SystemConstant.THIS_WEEK.equals(payForm.getSelect())) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            String startOfWeek = GetUtils.formatDateToString(calendar.getTime());
            times = listTime(payForm, calendar);
            calendar.add(Calendar.DAY_OF_WEEK, + 6);
            String endOfWeek = GetUtils.formatDateToString(calendar.getTime());
            payDtos = orderService.findAllByDayToDay(startOfWeek, endOfWeek);
            payDtos = addPayDtoIfMissTimes(payDtos, times);
        } else if (SystemConstant.THIS_MONTH.equals(payForm.getSelect())) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            String startOfMonth = GetUtils.formatDateToString(calendar.getTime());
            calendar.add(Calendar.MONTH, + 1);
            calendar.add(Calendar.DAY_OF_MONTH, - 1);
            times = listTime(payForm, calendar);
            String endOfMonth = GetUtils.formatDateToString(calendar.getTime());
            payDtos = orderService.findAllByDayToDay(startOfMonth, endOfMonth);
            payDtos = addPayDtoIfMissTimes(payDtos, times);
        } else if (SystemConstant.THIS_YEAR.equals(payForm.getSelect())) {
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            String startOfYear = GetUtils.formatDateToString(calendar.getTime());
            calendar.set(Calendar.MONTH, 12);
            calendar.add(Calendar.DAY_OF_MONTH, - 1);
            String endOfYear = GetUtils.formatDateToString(calendar.getTime());
            times = listTime(payForm, calendar);
            payDtos = orderService.findAllByMonthToMonth(startOfYear, endOfYear);
            payDtos = addPayDtoIfMissTimes(payDtos, times);
        } else if (SystemConstant.MORE.equals(payForm.getSelect())) {
            long day = calculatingDaysBetweenTwoDates(payForm.getSinceDate(), payForm.getToDate());
            payForm.setDay(day);
            payDtos = checkDay(payForm, calendar, day);
        }

        return payDtos;
    }

    private List<PayDto> checkDay(PayForm payForm, Calendar calendar, long day) {
        List<PayDto> payDtos;
        List<String> times;
        if (day <= 31) {
            payDtos = orderService.findAllByDayToDay(payForm.getSinceDate(), payForm.getToDate());
            times = listTime(payForm, calendar);
            payDtos = addPayDtoIfMissTimes(payDtos, times);
        } else if (day <= 366) {
            payDtos = orderService.findAllByMonthToMonth(payForm.getSinceDate(), payForm.getToDate());
            times = listTime(payForm, calendar);
            payDtos = addPayDtoIfMissTimes(payDtos, times);
        }
        else {
            payDtos = orderService.findAllByYearToYear(payForm.getSinceDate(), payForm.getToDate());
            times = listTime(payForm, calendar);
            payDtos = addPayDtoIfMissTimes(payDtos, times);
        }
        return payDtos;
    }

    private List<PayDto> addPayDtoIfMissTimes(List<PayDto> payDtos, List<String> times) {
        List<PayDto> results = new ArrayList<>();
        for (String time : times) {
            boolean isNotPresent = true;
            for (PayDto payDto : payDtos) {
                if (time.equals(payDto.getTime())) {
                    results.add(payDto);
                    isNotPresent = false;
                    break;
                }
            }
            if (isNotPresent) {
                setTimeToPayDtos(results, time);
            }
        }

        return results;
    }

    private List<String> listTime(PayForm payForm, Calendar calendar) {
        List<String> times = new ArrayList<>();
        if (SystemConstant.THIS_WEEK.equals(payForm.getSelect())) {
            times.add(GetUtils.formatDateToString(calendar.getTime()));
            for (int i = 0; i < 6; i++) {
                calendar.add(Calendar.DAY_OF_WEEK, + 1);
                times.add(GetUtils.formatDateToString(calendar.getTime()));
            }
        }
        if (SystemConstant.THIS_MONTH.equals(payForm.getSelect())) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd");
            int limit = Integer.parseInt(sdf.format(calendar.getTime()));
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            times.add(GetUtils.formatDateToString(calendar.getTime()));
            for (int i = 0; i < limit - 1; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, + 1);
                times.add(GetUtils.formatDateToString(calendar.getTime()));
            }
        }
        if (SystemConstant.THIS_YEAR.equals(payForm.getSelect())) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            int limit = Integer.parseInt(sdf.format(calendar.getTime()));
            calendar.set(Calendar.MONTH, 0);
            sdf = new SimpleDateFormat("yyyy-MM");
            times.add(sdf.format(calendar.getTime()));
            for (int i = 0; i < limit - 1; i++ ) {
                calendar.add(Calendar.MONTH, + 1);
                times.add(sdf.format(calendar.getTime()));
            }
        }
        if (SystemConstant.MORE.equals(payForm.getSelect())) {
            if (payForm.getDay() <= 31) {
                times.add(payForm.getSinceDate());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(payForm.getSinceDate());
                    calendar.setTime(date);
                    for (int i = 0; i < payForm.getDay(); i ++) {
                        calendar.add(Calendar.DAY_OF_MONTH, + 1);
                        times.add(sdf.format(calendar.getTime()));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (payForm.getDay() <= 366) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                try {
                    Date start = sdf.parse(payForm.getSinceDate());
                    Date end = sdf.parse(payForm.getToDate());
                    calendar.setTime(start);
                    times.add(sdf.format(calendar.getTime()));
                    while (true) {
                        calendar.add(Calendar.MONTH, + 1);
                        if (calendar.getTime().after(end)) {
                            break;
                        }
                        times.add(sdf.format(calendar.getTime()));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                try {
                    Date start = sdf.parse(payForm.getSinceDate());
                    Date end = sdf.parse(payForm.getToDate());
                    calendar.setTime(start);
                    times.add(sdf.format(calendar.getTime()));
                    while (true) {
                        calendar.add(Calendar.YEAR, + 1);
                        if (calendar.getTime().after(end)) {
                            break;
                        }
                        times.add(sdf.format(calendar.getTime()));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return times;
    }

    private void setTimeToPayDtos(List<PayDto> payDtos, String time) {
        PayDto payDto = new PayDto();
        payDto.setTime(time);
        payDto.setMoney(0.0);
        payDtos.add(payDto);
    }

    private long calculatingDaysBetweenTwoDates(String firstTime, String secondTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long diff = 0;
        try {
            Date first = sdf.parse(firstTime);
            Date second = sdf.parse(secondTime);
            diff = (second.getTime() - first.getTime()) / 1000 / 60 / 60 / 24;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diff;
    }



    @Override
    public List<PayDto> getRevenueByStringTime(String time) {
        List<PayDto> payDtos = orderService.findAllByDay(time);
        if (CollectionUtils.isEmpty(payDtos)) {
            setTimeToPayDtos(payDtos, time);
        }
        return payDtos;
    }

    @Override
    public double countTotalMoney(List<PayDto> payDtos) {
        double totalMoney = 0.0;
        for (PayDto payDto : payDtos) {
            totalMoney += payDto.getMoney();
        }
        return totalMoney;
    }

}
