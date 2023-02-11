package com.oussamabw.shopee.storage

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@Controller
class UploadFileController {

    @Autowired
    lateinit var fileStorage: FileStorage

    @PostMapping("/upload/")
    fun uploadMultipartFile(@RequestParam("uploadfile") file: MultipartFile, model: Model): ResponseEntity<Any> {
        val upfile = fileStorage.store(file)
        model.addAttribute("message", "File uploaded successfully! -> filename = " + file.originalFilename)

        val fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/download/")
            .path(upfile)
            .toUriString()

        return ResponseEntity.status(HttpStatus.OK)
            .body(FileResponse(upfile, fileDownloadUri, "File uploaded with success!"))
    }
}