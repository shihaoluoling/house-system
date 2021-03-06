package com.example.admin.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admin.center.dao.GameFileMapper;
import com.example.admin.center.model.GameFile;
import com.house.common.service.FileMangeService;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: FileController
 * @ProjectName Second-order-center
 * @Description: 文件
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/File")
@CrossOrigin
public class FileController {
    @Autowired
    private GameFileMapper gameFileMapper;
    private static final String LOCK = "LOCK";
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @RequestMapping(value = "/fileUpLoad", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> fileUpLoad(MultipartFile file) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
//        FileMangeService fileMangeService = new FileMangeService();
        arr = FileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
        GameFile fileDesc = new GameFile();
        fileDesc.setFileName(file.getName());
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(-1);
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDesc.setIsDeleted((short) 0);
        gameFileMapper.insert(fileDesc);
//        InetAddress ip = null;
//        ip=ip.getLocalHost();
//        String localname=ip.getHostName();
//        String localip=ip.getHostAddress();
//        String s = "https://swcloud.tjsichuang.cn:1444/second/user/File/getPicture?id=";
        String s = "http://39.99.157.7:7003/game/File/getPicture?id=";
        return builder.body(ResponseUtils.getResponseBody(s+String.valueOf(fileDesc.getId())));
    }
    //压缩过
    @ApiOperation(value = "获取图片", notes = "获取图片")
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    public void getPicture(Integer id, HttpServletResponse response) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        GameFile fileDesc = gameFileMapper.selectByPrimaryKey(id);
        if (fileDesc == null) {
            throw new Exception("file not exists");
        }
        FileMangeService fileManageService = new FileMangeService();
        synchronized (LOCK) {
            byte[] file = FileMangeService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
            InputStream sbs = new ByteArrayInputStream(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Thumbnails.of(sbs).scale(1f).outputFormat("jpg").outputQuality(0.7).toOutputStream(os);
            file = os.toByteArray();
            ByteArrayInputStream stream = new ByteArrayInputStream(file);
            BufferedImage readImg = ImageIO.read(stream);
            stream.reset();
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(readImg, "png", outputStream);
//            outputStream.close();
        }
    }
    @ApiOperation(value = "删除图片", notes = "删除")
    @RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> filedelete(Integer fileId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        GameFile fileDesc = gameFileMapper.selectByPrimaryKey(fileId);
        FileMangeService fileManageService = new FileMangeService();
        if (fileDesc != null) {
            FileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
        }
        gameFileMapper.deleteByPrimaryKey(fileId);
        return builder.body(ResponseUtils.getResponseBody(fileId));
    }
}
