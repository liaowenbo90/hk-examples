package com.hk.fs.controller;

import org.springframework.stereotype.Controller;

/**
 * @author kevin
 * @date 2018年1月26日下午12:48:54
 */
@Controller
public class FileController {

//    @Autowired
//    private FileHandler fileHandler;
//
//    /**
//     * 文件上传
//     *
//     * @param fileList
//     * @return
//     * @throws IOException
//     */
//    @PostMapping("/upload")
//    @ResponseBody
//    public JsonResult upload(@RequestParam("files") MultipartFile[] fileList) throws IOException {
//        if (ArrayUtils.isEmpty(fileList)) {
//            return JsonResult.failure("请至少选择一个文件！");
//        }
//        List<FileInfo> resultData = Lists.newArrayList();
//        for (MultipartFile file : fileList) {
//            String path = fileHandler.upload(file.getOriginalFilename(), file.getInputStream());
//            FileInfo fileInfo = new FileInfo();
//            fileInfo.setExt(Files.getFileExtension(file.getOriginalFilename()));
//            fileInfo.setFileName(file.getOriginalFilename());
//            fileInfo.setFilePath(path);
//            fileInfo.setFileSize(file.getSize());
//            resultData.add(fileInfo);
//        }
//        return JsonResult.success(resultData);
//    }
//
//    /**
//     * 下载文件
//     *
//     * @param filePath
//     * @return
//     */
//    @GetMapping("/down")
//    public ResponseEntity<byte[]> down(@RequestParam String filePath) {
//        byte[] dataByte = fileHandler.getFileData(filePath);
//        return Webs.toDownResponseEntity(filePath, dataByte);
//    }
//
//    /**
//     * 文件预览
//     *
//     * @param yyyy
//     * @param mm
//     * @param dd
//     * @param fileName
//     * @throws IOException
//     */
//    @GetMapping("/{yyyy}/{mm}/{dd}/{fileName:.+}")
//    public ResponseEntity<byte[]> view(@PathVariable String yyyy, @PathVariable String mm, @PathVariable String dd,
//                                       @PathVariable String fileName) throws IOException {
//        return Webs.toImageResponseEntity(fileHandler.getFileData(String.format("%s/%s/%s/%s", yyyy, mm, dd, fileName)));
//    }
}
