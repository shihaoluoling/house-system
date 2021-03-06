package com.example.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.user.center.dao.HouseFileMapper;
import com.example.user.center.model.HouseFile;
import com.house.common.service.FileMangeService;
import com.house.utils.response.handler.ResponseEntity;
import com.house.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;

/**
 * @author shihao
 * @Title: uploadFileController
 * @ProjectName Second-order-center
 * @Description: 文件上传
 * @date Created in
 * @Version: $
 */
@RestController
@Api
@RequestMapping("/File")
@CrossOrigin
public class UploadFileController {
    /**图片上传
     *
     * @param file 图片
     * @return
     * @throws Exception
     */
@Autowired
private HouseFileMapper houseFileMapper;
    private static final String LOCK = "LOCK";
    @ApiOperation(value = "上传图片", notes = "上传图片")
    @RequestMapping(value = "/fileUpLoad", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> fileUpLoad(MultipartFile file) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        String arr[];
//        FileMangeService fileMangeService = new FileMangeService();
        arr = FileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
        HouseFile fileDesc = new HouseFile();
        fileDesc.setFileName(file.getName());
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(-1);
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDesc.setIsDeleted((short) 0);
        houseFileMapper.insert(fileDesc);
//        InetAddress ip = null;
//        ip=ip.getLocalHost();
//        String localname=ip.getHostName();
//        String localip=ip.getHostAddress();
//        String s = "https://swcloud.tjsichuang.cn:1444/second/user/File/getPicture?id=";
        String s = "http://39.98.126.20:7004/user/File/getPicture?id=";
        return builder.body(ResponseUtils.getResponseBody(s+String.valueOf(fileDesc.getId())));
    }
    //压缩过
    @ApiOperation(value = "获取图片", notes = "获取图片")
    @RequestMapping(value = "/getPicture", method = RequestMethod.GET)
    public void getPicture(Integer id, HttpServletResponse response) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        HouseFile fileDesc = houseFileMapper.selectByPrimaryKey(id);
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
        HouseFile fileDesc = houseFileMapper.selectByPrimaryKey(fileId);
        FileMangeService fileManageService = new FileMangeService();
        if (fileDesc != null) {
            FileMangeService.deleteFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
        }
        houseFileMapper.deleteByPrimaryKey(fileId);
        return builder.body(ResponseUtils.getResponseBody(fileId));
    }

    @ApiOperation(value = "上传文件", notes = "上传图片")
    @RequestMapping(value = "/fileUpLoadFile", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> fileUpLoadFile(MultipartFile file) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder(HttpStatus.OK);
        //获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        //获取最后一个.的位置
        int lastIndexOf = originalFilename.lastIndexOf(".");
        //获取文件的后缀名 .xxx
        String suffix = originalFilename.substring(lastIndexOf+1);
        String arr[];
//        FileMangeService fileMangeService = new FileMangeService();
        arr = FileMangeService.uploadFile(file.getBytes(), String.valueOf("-1"));
        HouseFile fileDesc = new HouseFile();
        //todo 保存文件后缀名
        fileDesc.setFileName(file.getName());
        fileDesc.setSuffix(suffix);
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(-1);
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDesc.setIsDeleted((short) 0);
        houseFileMapper.insert(fileDesc);
//        InetAddress ip = null;
//        ip=ip.getLocalHost();
//        String localname=ip.getHostName();
//        String localip=ip.getHostAddress();
//        String s = "https://swcloud.tjsichuang.cn:1444/second/user/File/getPicture?id=";
        String s = "http://39.98.126.20:7004/user/File/getFile?id=";
        return builder.body(ResponseUtils.getResponseBody(s+String.valueOf(fileDesc.getId())));
    }
    @ApiOperation(value = "获取文件", notes = "获取图片")
    @RequestMapping(value = "/getFile", method = RequestMethod.GET)
    public void getFile(Integer id, HttpServletResponse response) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        HouseFile fileDesc = houseFileMapper.selectByPrimaryKey(id);
        if (fileDesc == null) {
            throw new Exception("file not exists");
        }
        response.addHeader("Content-Type", "application/"+fileDesc.getSuffix()+";charset=UTF-8");
//        FileMangeService fileManageService = new FileMangeService();
        synchronized (LOCK) {
            byte[] file = FileMangeService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
//            ByteArrayInputStream stream = new ByteArrayInputStream(file);
//            PDFTextStripper stripper = new PDFTextStripper();
            OutputStream outputStream = response.getOutputStream();
//            ImageIO.write(readImg, "png", outputStream);
            IOUtils.write(file, outputStream);
            outputStream.close();
        }
    }

    @PostMapping("/file/upload")
    public String fileUpload(@RequestParam("files")MultipartFile[] files){
        String filePath = "";
        // 多文件上传
        for (MultipartFile file : files){
            // 上传简单文件名
            String originalFilename = file.getOriginalFilename();
            // 存储路径
            filePath = new StringBuilder("C:\\Users\\MACHENIKE\\Desktop")
                    .append(System.currentTimeMillis())
                    .append(originalFilename)
                    .toString();
            try {
                // 保存文件
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }
}
