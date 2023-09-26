package net.spartanb312.superintendent.utils

import oshi.SystemInfo
import java.util.*

object HardwareUtils {

    val jreVersion = System.getProperty("java.version")

    val platform = Pair(
        System.getProperty("os.name")!!,
        System.getProperty("os.arch")!!
    ).let { (name, arch) ->
        when {
            arrayOf("Linux", "FreeBSD", "SunOS", "Unit").any { name.startsWith(it) } -> {
                if (arrayOf("arm", "aarch64").any { arch.startsWith(it) }) {
                    if (arch.contains("64") || arch.startsWith("armv8")) "Linux 64 bit for ARM"
                    else "Linux 32 bit for ARM"
                } else "Linux 64 bit"
            }
            arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } -> {
                if (arch.startsWith("aarch64")) "MacOS 64 bit for ARM"
                else "MacOS 64 bit"
            }
            arrayOf("Windows").any { name.startsWith(it) } -> {
                if (arch.contains("64")) {
                    if (arch.startsWith("aarch64")) "Windows 64 bit for ARM"
                    else "Windows 64 bit"
                } else "Windows 32 bit"
            }
            else -> throw Error("Unrecognized or unsupported platform!")
        }
    }

    val x64 = !platform.contains("32")
    val system = System.getProperty("os.name")!! + " " +
            if (platform.contains("ARM")) {
                if (x64) "ARM 64 Bit" else "ARM 32 Bit"
            } else if (x64) "64 Bit" else "32 Bit"

    fun getCpuName(isWindows: Boolean = true): String {
        return try {
            val process = if (isWindows) Runtime.getRuntime().exec("wmic cpu get name")
            else Runtime.getRuntime().exec("sysctl machdep.cpu.brand_string")
            process.outputStream.close()
            val scanner = Scanner(process.inputStream)
            var cpuName = ""
            while (scanner.hasNext()) {
                cpuName += scanner.next() + " "
            }
            cpuName.removeSuffix(" ").removePrefix("Name ")
        } catch (ignore: Exception) {
            "Unknown processor"
        }
    }

    val cpuName = try {
        val aprocessor = SystemInfo().hardware.processors
        String.format("%dx %s", aprocessor.size, aprocessor[0]).replace("\\s+".toRegex(), " ")
    } catch (ignore: Exception) {
        getCpuName(System.getProperty("os.name").startsWith("Windows"))
    }

}
