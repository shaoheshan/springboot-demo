package com.heshan.springboot.web.platform.modules.base.ctrl;

import com.heshan.springboot.web.platform.common.constant.Const;
import com.heshan.springboot.web.platform.common.exception.BizException;
import com.heshan.springboot.web.platform.config.VhscProperties;
import com.heshan.springboot.web.platform.common.utils.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 上传文件统一处理
 *
 * @author
 * @email Frank@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Slf4j
@RestController
@RequestMapping("/file")
public class FileUploadCtrl {
    private String upload = "upload";
    private String materialPath = "upload\\materialPic\\";
    
    @Autowired
    private VhscProperties vhscProperties;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * 上传文件
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    public PageResult upload(@RequestParam(value = Const.FIELD_NAME, required = true) MultipartFile file) {
        try {
            this.validateUploadFile(file);
            return PageResult.ok().put("paths", this.parseUploadFile(file));
        } catch (IOException e) {
            e.printStackTrace();
            return PageResult.error("上传文件出错");
        }
    }
    
    /**
     * 展示图片
     */
    @ResponseBody  
    @RequestMapping(method = RequestMethod.GET, value = "/download/{filename:.+}")  
    public ResponseEntity<?> getFile(@PathVariable String filename) {  
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + getPojectPath() + materialPath + filename));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();  
        }
    }
    
    /**
     * 展示图片
     */
    @ResponseBody  
    @RequestMapping(method = RequestMethod.GET, value = "/download/cartImg/{filename:.+}")  
    public ResponseEntity<?> download(@PathVariable String filename, @RequestParam("path") String path) {  
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + getPojectPath() + upload + path));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();  
        }
    }
    
    /** 验证上传的图片是否合法 */
    private void validateUploadFile(MultipartFile file) {
        if (file == null) {
            throw new BizException("无法获取上传文件");
        }

        if (file.isEmpty()) {
            throw new BizException("表单中的上传文件项不能为空");
        }

        long fileSize = file.getSize();
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);// 文件后缀名

        if (vhscProperties.getFileUploadMax() <= fileSize) {
            throw new BizException("文件：" + fileName + "超过上传文件规定大小");
        }

        if (ext == null || !Const.IMAGE_FILE_EXT.contains(ext.toLowerCase())) {
            throw new BizException("文件：" + fileName + "格式非法");
        }
        try {
            ImageInfo imgInfo = new ImageInfo();
            imgInfo.setInput(file.getInputStream()); // in can be InputStream or RandomAccessFile
            imgInfo.setDetermineImageNumber(true); // default is false
            imgInfo.setCollectComments(true); // default is false
            if (!imgInfo.check()) {
                throw new BizException("文件：" + fileName + "不是一张图片");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 业务逻辑校验上传文件（文件大小限制、后缀名限制、文件头限制）
     * <p>
     * //TODO 暂时只处理图片类型
     *
     * @param file 上传文件对象
     */
    private String parseUploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        // 文件后缀名
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 前端定义文件夹位置
        // 新文件名
        String newFileName = UUID.randomUUID().toString() + "." + ext;
        // 拼装文件相对路径。返回给前台。
        String fileSavePath = newFileName;// 保存的相对路径
        // 组装绝对路径。降图片保存到本地。
        String fileDiskPath = buildFileSavePath(materialPath) + newFileName;// 磁盘绝对路径
        log.info("上传文件持久化路径：" + fileDiskPath);
        file.transferTo(new File(fileDiskPath));
        return fileSavePath;
    }

    /**
     * 传入路径，是否是绝对路径，是绝对路径则直接返回，相对路径则以项目根目录为起点创建
     *
     * @param path
     * @return
     */
    private String buildFileSavePath(String path) {
        String fileSavePath = null;
        if (path.startsWith("/") || path.indexOf(":") > 0) {
            // 绝对路径则直接返回
            fileSavePath = path;
        } else {
            // 获取绝对路径
            fileSavePath = getPojectPath() + path;
        }
        File f = new File(fileSavePath);
        if (!f.exists()) {
            f.mkdirs();
        }
        return fileSavePath;
    }

    private String getPojectPath() {
        return System.getProperty("user.dir").substring(0, System.getProperty("user.dir").lastIndexOf("\\") + 1);
    }
}
