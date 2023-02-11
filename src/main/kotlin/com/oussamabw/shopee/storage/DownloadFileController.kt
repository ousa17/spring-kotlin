package com.oussamabw.shopee.storage

import com.oussamabw.shopee.FileInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.io.IOException
import java.util.stream.Collectors


@Controller
class DownloadFileController {
    @Autowired
    lateinit var fileStorage: FileStorage

    /*
     * Retrieve Files' Information
     */
    @GetMapping("/files")
    fun getListFiles(model: Model): String {
        val fileInfos: List<FileInfo> = fileStorage.loadFiles().map { path ->
            FileInfo(
                path.fileName.toString(),
                MvcUriComponentsBuilder.fromMethodName(
                    DownloadFileController::class.java,
                    "downloadFile", path.fileName.toString()
                ).build().toString()
            )
        }
            .collect(Collectors.toList())

        model.addAttribute("files", fileInfos)
        return "multipartfile/listfiles"
    }

    /*
     * Download Files
     */
    @GetMapping("/files/{filename}")
    fun downloadFile(@PathVariable filename: String): ResponseEntity<ByteArray> {
        val file = fileStorage.loadFile(filename)
        try {
            val data = file.file.readBytes()
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(data)
        } catch (e: IOException) {
            e.printStackTrace()
            throw Exception("")
        }
    }


}