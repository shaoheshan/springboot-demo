package com.heshan.springboot.web.platform.modules.sys.api;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heshan.springboot.web.platform.common.exception.BizException;
import com.heshan.springboot.web.platform.support.AbstractCtrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.heshan.springboot.web.platform.common.utils.PageResult;

@RestController
@RequestMapping("/api/v2/sys")
public class APISystemCtrl extends AbstractCtrl {
    @Value("${logging.path}")
    private String logPath;

    /**
     * 根据查询条件获取日志文件列表
     */
    @GetMapping("/logList")
    public PageResult logList(@RequestParam LogQueryPars query) {
        System.out.println(logPath);
        return PageResult.ok();
    }

    /**
     * 下载指定日志文件
     */
    @ResponseBody
    @GetMapping("/logFile")
    public void logDetail(String fileName, HttpServletRequest req, HttpServletResponse resp) {
        String filePath = req.getServletContext().getRealPath("/") + logPath + fileName;
        File file = new File(filePath);
        // TODO spring boot中WEB根目录的确定还需要细研
        String path = this.getClass().getResource("/").getPath();
        System.out.println(path);
        if (!file.exists()) {
            throw new BizException("没有找到日志文件：" + filePath);
        }
    }

    class LogQueryPars {
        private String fileName;// 文件名
        private Date startDate;// 日志开始日期
        private Date endDate;// 日志结束日期
        private Date updateDate;// 日志最后记录日期

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Date getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Date updateDate) {
            this.updateDate = updateDate;
        }

    }

}