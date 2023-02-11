package com.oussamabw.shopee.storage

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Date
import java.util.stream.Stream

/**
 * Created by IntelliJ IDEA.
 * Project : kotlin-spring-boot-upload-download-image
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2018-11-26
 * Time: 03:58
 * To change this template use File | Settings | File Templates.
 */
@Service
class FileStorageImpl : FileStorage {

    val log = LoggerFactory.getLogger(this::class.java)
    val rootLocation = Paths.get("filestorage")

    override fun store(file: MultipartFile): String {
        try {
            val fileName = "${Date().time}.${file.originalFilename?.let { StringUtils.getFilenameExtension(it) }}"
            Files.copy(file.inputStream, this.rootLocation.resolve(fileName))
            return fileName
        } catch (e: Exception) {
            throw FileNotFoundException("Could not upload file");
        }
    }

    override fun loadFile(filename: String): Resource {
        val file = rootLocation.resolve(filename)
        val resource = UrlResource(file.toUri())

        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw RuntimeException("FAIL!")
        }
    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile())
    }

    override fun init() {
        Files.createDirectory(rootLocation)
    }

    override fun loadFiles(): Stream<Path> {
        return Files.walk(this.rootLocation, 1)
            .filter { path -> !path.equals(this.rootLocation) }
            .map(this.rootLocation::relativize)
    }
}