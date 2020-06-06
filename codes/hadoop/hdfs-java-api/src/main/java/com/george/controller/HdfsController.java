package com.george.controller;

import com.george.common.Result;
import com.george.common.StatusCode;
import com.george.template.HdfsTemplate;
import org.apache.hadoop.fs.BlockLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  Hdfs 前端控制器
 * </p>
 *
 * @author GeorgeChan 2020/1/4 22:36
 * @version 1.0
 * @since jdk1.8
 */
@RestController
@RequestMapping("/hdfs")
public class HdfsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HdfsController.class);

    private final HdfsTemplate hdfsTemplate;

    @Autowired
    public HdfsController(HdfsTemplate hdfsTemplate) {
        this.hdfsTemplate = hdfsTemplate;
    }

    /**
     * 判断文件夹是否存在
     * @param dirPath 文件夹路径
     * @return 返回结果
     */
    @PostMapping("/exitDir")
    public Result existDir(@RequestParam(value = "dirPath", required = false) String dirPath) {
        boolean res;
        try {
            res = hdfsTemplate.existDir(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        if (res) {
            return new Result(true, StatusCode.OK, "该文件夹已存在");
        }
        return new Result(true, StatusCode.ERROR, "该文件不存在");
    }

    /**
     * 创建文件夹
     * @param dirPath 文件夹路径
     * @return 返回结果
     */
    @PostMapping("/mkDir")
    public Result mkDir(@RequestParam(value = "dirPath", required = false) String dirPath) {
        boolean res;
        try {
            res = hdfsTemplate.mkDir(dirPath);
            if (res) {
                return new Result(true, StatusCode.OK, "创建成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        return new Result(true, StatusCode.ERROR, "创建失败");
    }

    /**
     * 获取目录的信息
     * @param dirPath 目录路径
     * @return 返回结果
     */
    @PostMapping("/readPathInfo")
    public Result readPathInfo(@RequestParam(value = "dirPath", defaultValue = "/", required = false) String dirPath) {
        try {
            List<Map<String, Object>> pathInfo = hdfsTemplate.readPathInfo(dirPath);
            return new Result(true, StatusCode.OK, "操作成功", pathInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "操作失败");
        }
    }

    /**
     * HDFS创建文件
     * @param filePath 文件路径
     * @param file 创建的文件
     * @return
     */
    @PostMapping("/createFile")
    public Result createFile(@RequestParam(value = "filePath", required = false) String filePath,
                             MultipartFile file) {
        try {
            boolean res = hdfsTemplate.createFile(filePath, file);
            if (res) {
                return new Result(true, StatusCode.OK, "上传成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "上传失败");
        }
        return new Result(false, StatusCode.ERROR, "上传失败");
    }

    /**
     * 文件读取
     * @param filePath 文件路径
     * @return 返回结果
     */
    @PostMapping("/readFile")
    public Result readFile(@RequestParam(value = "filePath", required = false) String filePath) {
        try {
            String file = hdfsTemplate.readFile(filePath);
            return new Result(true, StatusCode.OK, "读取成功", file);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "读取失败");
        }
    }

    /**
     * 获取文件列表
     *
     * @param dirPath 路径
     * @return 返回结果
     */
    @PostMapping("/getFileList")
    public Result getFileList(@RequestParam(value = "dirPath", required = false) String dirPath) {
        try {
            List<Map<String, String>> files = hdfsTemplate.listFile(dirPath);
            return new Result(true, StatusCode.OK, "读取成功", files);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "读取失败");
        }
    }

    /**
     * 文件重命名
     *
     * @param oldName 旧文件名(文件路径 + 名子)
     * @param newName 新文件名(文件路径 + 名子)
     * @return 是否重命名成功
     */
    @PostMapping("/renameFile")
    public Result renameFile(@RequestParam(value = "oldName", required = false) String oldName,
                             @RequestParam(value = "newName", required = false) String newName) {
        boolean res;
        try {
            res = hdfsTemplate.renameFile(oldName, newName);
            if (res) {
                return new Result(true, StatusCode.OK, "操作成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        return new Result(false, StatusCode.ERROR, "操作失败");
    }

    /**
     * 删除HDFS文件或文件夹
     * @param filePath 文件路径
     * @return 是否删除成功返回
     */
    @PostMapping("/deleteFile")
    public Result deleteFile(@RequestParam(value = "filePath", required = false) String filePath) {
        boolean res;
        try {
            res = hdfsTemplate.deleteFile(filePath);
            if (res) {
                return new Result(true, StatusCode.OK, "操作成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        return new Result(false, StatusCode.ERROR, "操作失败");
    }

    /**
     * 文件上传
     *
     * @param filePath   客户端文件路径
     * @param uploadPath 上传到服务器的路径
     * @return 是否上传成功
     */
    @PostMapping("/uploadFile")
    public Result uploadFile(@RequestParam(value = "filePath", required = false) String filePath,
                             @RequestParam(value = "uploadPath", required = false) String uploadPath) {
        boolean res;
        try {
            res = hdfsTemplate.uploadFile(filePath, uploadPath);
            if (res) {
                return new Result(true, StatusCode.OK, "上传成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        return new Result(false, StatusCode.ERROR, "上传失败");
    }

    /**
     * 文件下载
     *
     * @param clientFilePath 客户端路径
     * @param serverFilePath 服务端路径
     * @return 是否下载成功
     */
    @PostMapping("/downloadFile")
    public Result downloadFile(@RequestParam(value = "clientFilePath", required = false) String clientFilePath,
                             @RequestParam(value = "serverFilePath", required = false) String serverFilePath) {
        boolean res;
        try {
            res = hdfsTemplate.downloadFile(clientFilePath, serverFilePath);
            if (res) {
                return new Result(true, StatusCode.OK, "操作成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        return new Result(false, StatusCode.ERROR, "操作失败");
    }

    /**
     * 文件复制
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标路径
     * @return 是否复制成功
     */
    @PostMapping("/copyFile")
    public Result copyFile(@RequestParam(value = "sourcePath", required = false) String sourcePath,
                               @RequestParam(value = "targetPath", required = false) String targetPath) {
        boolean res;
        try {
            res = hdfsTemplate.copyFile(sourcePath, targetPath);
            if (res) {
                return new Result(true, StatusCode.OK, "操作成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        return new Result(false, StatusCode.ERROR, "操作失败");
    }

    /**
     * 获取某个文件在HDFS的集群位置
     *
     * @param filePath 文件路径
     * @return 返回结果
     */
    @PostMapping("/getFileBlockLocations")
    public Result getFileBlockLocations(@RequestParam(value = "filePath", required = false) String filePath) {
        try {
            BlockLocation[] fileBlockLocations = hdfsTemplate.getFileBlockLocations(filePath);
            if (!Objects.isNull(fileBlockLocations)) {
                return new Result(true, StatusCode.OK, "操作成功", fileBlockLocations);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "系统异常");
        }
        return new Result(false, StatusCode.ERROR, "操作失败");
    }
}
