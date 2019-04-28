package com.rishiqing.dingtalk.web.dingcloud.controller.app;

import com.rishiqing.dingtalk.api.service.biz.CorpQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Da Shuai
 * @create: 2019-04-26 18:06
 */
@RestController
@RequestMapping("check")
public class CheckController {
    @Autowired
    private CorpQueryService corpQueryService;

    @GetMapping("staffCount/aboveThreshold")
    public Boolean isAboveCorpStaffCountThreshold(@RequestParam() String corpId) {
        return corpQueryService.isAboveCorpStaffCountThreshold(corpId);
    }
}
