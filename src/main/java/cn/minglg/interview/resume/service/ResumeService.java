package cn.minglg.interview.resume.service;

import cn.minglg.interview.common.response.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName:ResumeService
 * Package:cn.minglg.interview.resume.service
 * Description:简历服务
 *
 * @Author kfzx-minglg
 * @Create 2025/7/23
 * @Version 1.0
 */
public interface ResumeService {

    /**
     * 简历上传接口
     *
     * @param file 文件对象
     * @return 上传结果
     */
    R resumeUpload(MultipartFile file);

    /**
     * 获取文件扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名
     */
    default String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }

    /**
     * 文件类型白名单校验
     *
     * @param filename       文件名
     * @param allowFileTypes 是否在允许上传的白名单中
     * @return 验证结果
     */
    default boolean isValidFileType(String filename, List<String> allowFileTypes) {
        String extension = getFileExtension(filename).toLowerCase();
        return allowFileTypes.contains(extension);
    }

}
