package com.spread.st

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class Wt : Plugin<Project> {

    companion object {
        private const val BUFFER_SIZE = 2 * 1024
    }

    override fun apply(target: Project) {
        println("enter wt")
        target.afterEvaluate {
            val rootDir = this.rootDir
            println("root dir: ${rootDir.absolutePath}")
            val asset = target.extensions.getByType(AppExtension::class.java).sourceSets.getByName("main").assets.srcDirs.toTypedArray()[0]
            val zipFile = File(asset, "wt.zip")
            FileOutputStream(zipFile).use { fos ->
                fos.toZip(rootDir.absolutePath)
            }
        }
    }

    private fun FileOutputStream.toZip(path: String) = ZipOutputStream(this).use { zos ->
        val file = File(path)
        zos.compress(file, file.name, true)
    }

    private fun ZipOutputStream.compress(
        file: File,
        name: String,
        keepDirStructure: Boolean
    ) {
        val buf = ByteArray(BUFFER_SIZE)
        if (file.isFile) {
            if (file.absolutePath.endsWith(".zip")) {
                return
            }
            println("zip: ${file.name}")
            putNextEntry(ZipEntry(name))
            var len: Int
            FileInputStream(file).use { fis ->
                len = fis.read(buf)
                while (len != -1) {
                    write(buf, 0, len)
                    len = fis.read(buf)
                }
            }
            closeEntry()
        } else {
            val listFiles = file.listFiles()
            if (listFiles == null || listFiles.isEmpty()) {
                if (keepDirStructure) {
                    putNextEntry(ZipEntry("$name/"))
                    closeEntry()
                }
            } else {
                for (f in listFiles) {
                    if (keepDirStructure) {
                        compress(f, "${name}/${f.name}", true)
                    } else {
                        compress(f, f.name, false)
                    }
                }
            }
        }
    }


}