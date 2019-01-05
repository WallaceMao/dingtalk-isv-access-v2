package com.rishiqing.dingtalk.biz.converter.corp;

import com.csvreader.CsvWriter;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpAuthInfoVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpCountWithCreatorVO;
import com.rishiqing.dingtalk.isv.api.model.corp.CorpVO;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-07 1:45
 */
public class CorpConverter {
    public static CorpVO corpAuthInfoVO2CorpVO(CorpAuthInfoVO corpAuthInfo, Long scopeVersion) {
        if (corpAuthInfo == null || corpAuthInfo.getAuthCorpInfo() == null) {
            return null;
        }
        CorpAuthInfoVO.AuthCorpInfo authCorpInfo = corpAuthInfo.getAuthCorpInfo();
        CorpVO corpVO = new CorpVO();
        corpVO.setCorpId(authCorpInfo.getCorpId());
        corpVO.setCorpLogoUrl(authCorpInfo.getCorpLogoUrl());
        corpVO.setCorpName(authCorpInfo.getCorpName());
        corpVO.setIndustry(authCorpInfo.getIndustry());
        corpVO.setInviteCode(authCorpInfo.getInviteCode());
        corpVO.setInviteUrl(authCorpInfo.getInviteUrl());
        corpVO.setScopeVersion(scopeVersion);
        return corpVO;
    }

    public static File CorpCountWithCreatorVO2CsvWriter(List<CorpCountWithCreatorVO> corpCountWithCreatorVOList) throws Exception {
        if (corpCountWithCreatorVOList == null) {
            return null;
        }
        //写入临时文件
        CsvWriter csvWriter = null;
        File tempFile = File.createTempFile("corp", ".csv");
        csvWriter = new CsvWriter(tempFile.getCanonicalPath(), ',', Charset.forName("utf-8"));
        //写入表头
        String[] heads = {"id", "公司id", "公司名称", "创建人id", "创建人名称", "创建时间"};
        csvWriter.writeRecord(heads);
        //写内容
        for (CorpCountWithCreatorVO corpCountWithCreatorVO : corpCountWithCreatorVOList) {
            csvWriter.write(corpCountWithCreatorVO.getId().toString());
            csvWriter.write(corpCountWithCreatorVO.getCorpId());
            csvWriter.write(corpCountWithCreatorVO.getCorpName());
            csvWriter.write(corpCountWithCreatorVO.getCreatorUserId());
            csvWriter.write(corpCountWithCreatorVO.getCreatorName());
            csvWriter.write(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(corpCountWithCreatorVO.getGmtCreate()));
            csvWriter.endRecord();
        }
        csvWriter.close();
        return tempFile;
    }
}
