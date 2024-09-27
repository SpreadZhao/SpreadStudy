package com.spread.common.cs

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

object FakeNetwork {

    private val fakeNews = listOf(
        News(
            "Android Security Bulletin—February 2024",
            "2024-02-01",
            " Issues are described in the tables below and include CVE ID, associated references, type of vulnerability, severity, and updated AOSP versions"
        ),
        News(
            "Linux • WeChat crashing - WineHQ Forums",
            "2022-09-14",
            "I was able to install WeChat correctly, scanned the QR code and even synced my chats. However very quickly, after a few seconds, It always crashes."
        ),
        News(
            "如何使用 Kotlin 协程编写高性能应用程序",
            "2024-02-22",
            "这是一个关于如何使用 Kotlin 协程编写高性能应用程序的教程。"
        ),
        News(
            "Android Studio 4.2 发布",
            "2024-02-21",
            "Android Studio 4.2 现已发布！此版本包含许多新功能和改进，包括对 Android 13 的支持、新的布局编辑器和改进的性能。"
        ),
        News(
            "2024 年北京冬奥会闭幕",
            "2024-02-20",
            "2024 年北京冬奥会于 2024 年 2 月 20 日闭幕。中国队以 9 金牌、4 银牌和 2 铜牌位列奖牌榜首位。"
        ),
        News(
            "美国宇航局宣布将于 2025 年登陆火星",
            "2024-02-19",
            "美国宇航局宣布将于 2025 年发射“阿尔忒弥斯 3”任务，并将宇航员送上火星。"
        ),
        News(
            "俄罗斯和乌克兰之间的紧张局势升级",
            "2024-02-18",
            "俄罗斯在乌克兰边境部署了大量军队，引发了西方国家的担忧。"
        ),
        // ... 94 个其他新闻条目 ...
        News("如何制作美味的巧克力蛋糕", "2024-02-22", "本教程将教您如何制作美味的巧克力蛋糕。"),
        News(
            "2024 年诺贝尔奖获得者揭晓",
            "2024-02-22",
            "2024 年诺贝尔奖获得者于 2024 年 2 月 22 日揭晓。物理学奖授予了三位科学家，他们发现了引力波；化学奖授予了开发了一种新的合成方法的科学家；医学奖授予了发现了癌症治疗方法的科学家。"
        ),
        News(
            "中国成功发射新一代载人飞船",
            "2024-02-22",
            "中国于 2024 年 2 月 22 日成功发射了新一代载人飞船。该飞船将用于未来的载人航天任务，包括载人登月。"
        ),

        News(
            "全球经济增长放缓",
            "2024-02-22",
            "国际货币基金组织 (IMF) 在 2024 年 2 月 22 日发布的一份报告中表示，全球经济增长将在 2024 年放缓至 3.6%。"
        ),

        News(
            "人工智能在医疗领域取得突破性进展",
            "2024-02-22",
            "科学家们开发了一种新的 AI 算法，可以帮助医生更准确地诊断癌症。"
        ),

        News(
            "气候变化导致全球灾害频发",
            "2024-02-22",
            "联合国发布报告称，气候变化导致全球灾害频发。报告指出，2023 年全球共发生 1000 多起自然灾害，造成数千亿美元的经济损失。"
        ),

        News(
            "美国和中国在贸易问题上达成协议",
            "2024-02-22",
            "美国和中国于 2024 年 2 月 22 日签署了一项新的贸易协议。该协议旨在解决两国之间的贸易争端，并促进双边贸易增长。"
        ),
        News(
            "Apple unveils new MacBook Pro with M2 chip",
            "2024-03-10",
            "Apple has officially announced the new MacBook Pro featuring the highly anticipated M2 chip, promising significant performance improvements."
        ),
        News(
            "SpaceX launches new satellite constellation for global internet coverage",
            "2024-03-15",
            "SpaceX has successfully launched a new constellation of satellites aimed at providing global internet coverage, marking a major milestone in the space industry."
        ),
        News(
            "Scientists discover new species of deep-sea fish",
            "2024-03-20",
            "A team of scientists has identified and classified a new species of deep-sea fish, shedding light on the biodiversity of our oceans."
        ),
        News(
            "Google announces breakthrough in quantum computing",
            "2024-03-25",
            "Google has made a significant breakthrough in quantum computing, achieving quantum supremacy in a complex computational task."
        ),
        News(
            "World Health Organization declares new strain of flu virus",
            "2024-03-28",
            "The World Health Organization has declared a new strain of flu virus, prompting global health authorities to ramp up surveillance and response efforts."
        )
    )

    private fun generateNews(): List<News> {
        val count = 10
        val res = mutableListOf<News>()
        repeat(count) {
            val index = Random.nextInt(0, fakeNews.size)
            res.add(fakeNews[index])
        }
        return res
    }

    suspend fun getNewsList(): List<News> = withContext(Dispatchers.IO) {
        delay(3000)
        generateNews()
    }
}