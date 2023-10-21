package com.amand.service;

import com.amand.dto.PayDto;
import com.amand.form.PayForm;

import java.util.List;

public interface IReportService {

    List<PayDto> getRevenueByForm(PayForm payForm);

    List<PayDto> getRevenueByStringTime(String time);

    double countTotalMoney(List<PayDto> payDtos);
}
