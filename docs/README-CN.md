<p align="center">
  <img src="../fastlane/metadata/android/en-US/images/icon.png" width="250" height="250" />
</p>

<h1 align="center">MpvRx</h1>

<p align="center">
  <b>基于 libmpv 的功能丰富、高效的 Android 视频播放器。</b>
  <br>
  <i>无广告。无追踪器。无干扰。只是一款表面平静、内核锋利的专业视频播放器。</i>
</p>

> [!IMPORTANT]
> ## MPV RX — 最终版本通告（2026年6月4日）
>
> **MPV RX 已进入最终开发阶段。**
>
> **1.4.1** 版本标记着本项目的**最后正式版本**。自此之后，将**不再有新功能、重大新增或活跃开发**。
>
> 我知道还有一些功能可能只完成了一部分，但大多数主要功能已达到最终阶段并正常工作。播放器已经成长了很多，此时的 MPV RX 已经达到了它应有的最佳状态。
>
> 未来只有在重要功能出问题时才会有更新，例如 API 端点变更、兼容性问题或关键修复。即便如此，也只有在我有时间的时候才会处理。
>
> 另外，有时候正常观看视频也是必要的——不是每个小 bug 都需要开庭审理、写 40 行的问题报告和情感伤害。😭
> 享受播放器，享受你的电影，让 MPV RX 做它该做的事。
>
> 非常感谢大家在这段旅程中的支持、反馈、问题报告、建议和关爱。你们帮助这个项目成为了**<ins>互联网上功能最丰富的视频播放器之一。</ins>**
>
> 我希望这个仓库未来能触达更广泛的受众。
>
> **感谢你们的一切！❤️**

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-brightgreen.svg" />
  <img src="https://img.shields.io/badge/License-Apache--2.0-blue.svg" />
  <img src="https://img.shields.io/github/v/release/Riteshp2001/mpvRx.svg?logo=github&label=Release&cacheSeconds=3600" />
  <img src="https://img.shields.io/github/downloads/Riteshp2001/mpvRx/total?logo=github&cacheSeconds=3600" />
</p>

---

## 截图展示

<div align="center">
  <img src="../fastlane/metadata/android/en-US/images/phoneScreenshots/player.png" width="92%">
</div>

<br>

<div align="center">
  <img src="../fastlane/metadata/android/en-US/images/phoneScreenshots/videoscreen.png" width="31%">
  <img src="../fastlane/metadata/android/en-US/images/phoneScreenshots/pip.png" width="31%">
  <img src="../fastlane/metadata/android/en-US/images/phoneScreenshots/about.jpg" width="31%">
</div>

<br>

<div align="center">
  <img src="../fastlane/metadata/android/en-US/images/phoneScreenshots/playlistwindow.png" width="48%">
  <img src="../fastlane/metadata/android/en-US/images/phoneScreenshots/chapters.png" width="48%">
</div>

---

## 功能特性

MpvRx 将 mpv-android 的体验推向了新的高度，具备深度自定义、热感知性能和独特的品质生活功能。以下是其独到之处：

<details open>
<summary><b>🎨 主题与视觉系统</b></summary>

| 功能 | 描述 |
|---|---|
| **25+ 款色彩主题** | 默认、动态（Material You）、Catppuccin、Nord、东京之夜、玫瑰松、Gruvbox、Dracula 等更多 |
| **AMOLED 纯黑模式** | 每款主题都配有纯黑背景的专属变体 |
| **播放器控件动画** | 5 种动画风格：默认、弹性弹跳、电影缩放、向上滑入、极简淡出 |
| **始终深色模式** | 选项使播放器控件始终保持深色主题，不受应用主题影响 |
| **主题化播放器控件** | 自适应控件，与应用主题或系统强调色匹配 |

</details>

<details open>
<summary><b>🖐️ 手势系统</b></summary>

| 功能 | 描述 |
|---|---|
| **精细化点击逻辑** | 可配置的双击跳转区域（左/中/右），每个区域可独立分配操作 |
| **多击连续跳转** | 三击/四击可继续向前跳转，无需抬手 |
| **水平滑动跳转** | 在视频上滑动以跳转，带实时时间/增量叠加层 |
| **长按动态加速** | 长按激活可配置的速度提升，左右滑动在 8 个预设间调整 |
| **字幕拖拽手势** | 字幕激活时，长按屏幕中央可垂直拖拽字幕位置 |
| **捏合缩放与平移** | 捏合缩放（-1x 到 3x），支持同时平移和缩放后单指平移 |
| **手势音量增强** | 垂直滑动音量可超过 100% 进入可配置的提升范围 |
| **交换音量/亮度区域** | 可交换屏幕两侧分别控制音量和亮度的设定 |

</details>

<details open>
<summary><b>📺 HDR 与视频管线</b></summary>

| 功能 | 描述 |
|---|---|
| **基于着色器的 HDR 管线** | 由 [hdr-toys](https://github.com/natural-harmonia-gropius/hdr-toys) 驱动 — 77 个内置 GLSL 着色器 |
| **四种 HDR 模式** | BT.2100 PQ（HDR10）、BT.2100 HLG、BT.2020 色域映射、线性 HDR |
| **SDR 转 HDR 增强** | 使用线性 HDR 管线时将 SDR 内容提升至 HDR 范围 |
| **GPU 去色带** | CPU（gradfun）或 GPU 去色带，可配置迭代次数、阈值、范围、颗粒 |
| **智能渲染后端** | 根据设备支持自动选择 OpenGL/Vulkan 和 gpu/gpu-next |

</details>

<details open>
<summary><b>🔥 热管理与电池</b></summary>

| 功能 | 描述 |
|---|---|
| **ThermalMonitor** | 播放期间每 10 秒采样热余量 |
| **自适应着色器预算** | 根据热余量自动限制环境着色器预算 |
| **Anime4K 主动降级** | 当热余量低于 40% 时自动降低 Anime4K 质量 |
| **后台轮询优化** | 控件隐藏时位置轮询间隔翻倍，减少 50% 的 JNI 唤醒 |
| **统计轮询退避** | 暂停时统计页轮询循环从 1 秒退避到 2 秒 |

</details>

<details open>
<summary><b>🧩 Anime4K 与超分辨率</b></summary>

| 功能 | 描述 |
|---|---|
| **7 个预设质量等级** | 关闭、A、B、C、A+、B+、C+，可干净切换 |
| **解码器设置中的质量等级** | 快速/均衡/高质量选择 |
| **4K/8K 安全防护** | 对高分辨率内容自动禁用 Anime4K |
| **热保护选择** | 在掉帧前根据热压力自动降低质量等级 |

</details>

<details open>
<summary><b>💡 环境光模式</b></summary>

| 功能 | 描述 |
|---|---|
| **两种视觉模式** | GLOW 和 FRAME_EXTEND — 均在运行时通过自定义 GLSL 渲染 |
| **15+ 个可配置参数** | 模糊采样、发光强度、饱和度、暖色调、暗角、抖动噪点等 |
| **着色器重编译缓存** | 当参数与上次编译版本匹配时跳过重编译 |

</details>

<details open>
<summary><b>📝 字幕系统</b></summary>

| 功能 | 描述 |
|---|---|
| **双字幕支持** | 主字幕 + 次字幕，带自动偏移防止重叠 |
| **ASS 覆盖模式** | 为次字幕提供智能强制/缩放处理 |
| **全面样式设置** | 字体、大小、粗体、斜体、边框、阴影、颜色、对齐、窗口缩放 |
| **三种在线搜索模式** | Wyzie、SubtitleHub（6 个聚合源）和 Hybrid（两者合并） |
| **TMDB 集成** | 完整的媒体搜索，支持季/集浏览查找字幕 |

</details>

<details open>
<summary><b>🎮 播放器控件</b></summary>

| 功能 | 描述 |
|---|---|
| **完全自定义布局** | 四个可配置区域（左上/右上、左下/右下）+ 竖屏底部行 |
| **24+ 种按钮类型** | 镜像、垂直翻转、A-B 循环、自定义跳过、后台播放、环境光等 |
| **自定义用户按钮** | 创建执行 Lua、JavaScript 或 mpv 命令的任意按钮 |
| **横竖屏自适应布局** | 每个方向完全不同的控件布局 |
| **滑动解锁控件** | 控件锁定时使用滑动机制解锁 |
| **隐藏按钮背景** | 透明按钮，仅图标可见 |
| **集中化"更多面板"** | 快速访问所有播放器按钮和自定义控件 |
| **播放器内设置** | 无需离开播放即可切换 10+ 项设置 |

</details>

<details open>
<summary><b>🧭 智能方向</b></summary>

| 功能 | 描述 |
|---|---|
| **8 种方向模式** | 自由、视频（自动宽高比）、竖屏、反向竖屏、传感器竖屏、横屏、反向横屏、传感器横屏 |
| **按视频持久记忆** | 每个视频独立记忆方向设置，跨会话保留 |

</details>

<details open>
<summary><b>🔍 文件浏览器与导航</b></summary>

| 功能 | 描述 |
|---|---|
| **双浏览模式** | 专辑视图（文件夹网格）和树状视图（文件管理器层级） |
| **文件夹置顶** | 将常用文件夹置顶 |
| **单子项自动展开** | 只有一个子文件夹时自动展开以加快浏览 |
| **自动滚动到上次播放** | 打开后自动定位到上次播放的视频位置 |
| **递归文件/文件夹统计** | 递归计算并显示总视频数、时长、大小 |
| **"新"标签** | 可配置的新视频指示器阈值 |
| **网格/列表布局** | 每个方向独立的列数设置 |
| **多协议网络** | 内置 SMB、FTP 和 WebDAV 客户端 |

</details>

<details open>
<summary><b>🤖 AI 集成</b></summary>

| 功能 | 描述 |
|---|---|
| **提供商支持** | OpenCode 和 Groq，可配置 API 密钥和模型 |
| **AI 字幕翻译** | 使用自定义提示词翻译字幕 |
| **AI 字幕格式化** | 使用自定义提示词重新格式化字幕样式 |
| **AI 文件重命名** | 使用自定义重命名提示词批量重命名视频文件 |

</details>

<details open>
<summary><b>📜 脚本与编辑器</b></summary>

| 功能 | 描述 |
|---|---|
| **双语言支持** | Lua（.lua）和 JavaScript（.js）脚本支持 |
| **Sora 代码编辑器** | 内置编辑器，支持 TextMate 语法高亮 |
| **运行时加载脚本** | 无需重启即可启用/禁用脚本 |
| **配置编辑器** | 内置 mpv.conf 和 input.conf 编辑器 |

</details>

<details open>
<summary><b>⚙️ 实用工具</b></summary>

| 功能 | 描述 |
|---|---|
| **统计页面 6** | 实时系统监控：FPS、丢帧、编解码器、网络曲线图、电池 |
| **视频压缩器** | 内置基于 FFmpeg 的压缩，带预设 |
| **12 种视频滤镜预设** | 生动、电影、戏剧、吉卜力风格、霓虹流行、深沉黑等 |
| **自定义跳过分段** | 从 IntroDB、TIDB、AniSkip、Anime Skip 检测片头/片尾/回顾/字幕/预告 |
| **A-B 循环** | 播放器内循环，进度条上有视觉标记 |
| **逐帧导航** | 逐帧前进/后退，带帧数显示 |
| **定时关闭** | 内置定时器，带快速预设（15/30/45/60 分钟） |
| **自适应后台播放** | 按 Home 键自动进入画中画，屏幕解锁后自动恢复 |
| **通知样式** | 无、媒体、或带章节的进度（Android 16+） |
| **安全区域/窗口偏移** | 防止摄像头挖孔遮挡 |
| **显示挖孔模式** | 挖孔设备上的全屏显示 |
| **记忆亮度** | 持久保留播放期间设置的亮度级别 |
| **M3U 播放列表支持** | 解析并播放本地 M3U 播放列表 |
| **yt-dlp 集成** | 通过原生 Python 桥接支持 YouTube、Twitch、Bilibili 等高性能流媒体播放（SDK 29+ 绕过） |

</details>

---

## 🔋 Mpv 电池优化指南

> **第一条专业提示：如果你是新手，请保持 mpv.conf 为空。**

- **使用 `gpu` 而不是 `gpu-next`** — gpu-next 是基于 Vulkan 的渲染器，在播放普通视频时会让 GPU 毫无意义地保持唤醒状态。经典的 `gpu` 后端更轻量，使用 OpenGL 驱动栈，在大多数 Android 设备上具有更好的功耗特性。
- **完全禁用 Vulkan。** Vulkan 在视频播放方面表现出色，但也很耗电。
- **使用 `fast` mpv 配置文件。** 它直接内置在 mpvRx 中，使用 Mpv 配置文件并将其设为默认，或在 _mpv.conf_ 中设置 `profile=fast`。
- **不要使用着色器。** 你正在使用的 Anime4K 预设正是消耗电池的元凶。着色器在 GPU 上每帧都运行。如果你正在观看 24fps 的内容并且运行了着色器管线，恭喜——你每秒做了 24 次不必要的 GPU 计算，而在手机屏幕上几乎看不到任何可见的好处。
- **不要使用 AI 生成的配置。** 就是说你，那个从 Reddit 复制了 200 行 `scale=ewa_lanczossharp`、`dscale=mitchell`、`cscale=sinc` 和一堆 `glsl-shaders` 配置的人。大多数人都不知道这些是干什么的。你只是让你的手机像准备 4K 影院投影一样渲染视频——在一个 6 英寸的屏幕上。动动脑子，这是你的 Android 手机，不是什么 4K 电视。

**我的观点：** mpv 的默认配置加上 `profile=fast` 和 `gpu` 后端播放视频时电池消耗微乎其微——通常**比** OEM 播放器还低，因为 mpv 没有数十亿个专有 DRM 模块、分析 SDK 和广告框架在后台消耗 CPU。下次你观看 2 小时电影电池下降超过 20-25% 时，不要怪 mpv。怪你盲目复制粘贴的那 14 个着色器。

_专业提示：如果你播放时的电池消耗保持在 200 mAh 以下和 0.9W 以下（查看 MpvRx 的统计页面 6 → 更多设置 → Page6），那你的 mpv 配置对视频观看来说就是合适的。这是我实验得出的结论，其他的我不太了解细节技术，如果有人想给我深入指导，请保留给自己，我不想听。_

---

<div align="center">
  <a href="https://github.com/Riteshp2001/mpvRx/releases">
    <img src="https://img.shields.io/badge/下载-稳定版本-blue?style=for-the-badge&logo=github" alt="稳定版本">
  </a>
</div>

如果有问题、感觉不对或需要进一步完善，_不要提交只有你需要的垃圾功能请求，这会被自动删除_，请在 [Issues](https://github.com/Riteshp2001/mpvRx/issues) 中报告。

---

## 构建指南

### 环境要求

- JDK 17
- 安装有现代构建工具的 Android SDK
- Git

### Debug 构建

```powershell
./gradlew.bat :app:assembleStandardDebug
```

### 发布变体

| 变体 | 描述 |
|---|---|
| `standard` | 主发布版，支持应用内更新 |

### APK 变体

| 变体 | 描述 |
|---|---|
| `universal` | 适用于所有支持的设备 |
| `arm64-v8a` | 推荐大多数现代 Android 设备使用 |
| `armeabi-v7a` | 适用于较旧的 32 位 ARM 设备 |
| `x86` | 适用于 32 位 Intel 和 AMD Android 设备 |
| `x86_64` | 适用于 64 位 Intel 和 AMD Android 设备 |

---

## 支持项目

如果你觉得 MpvRx 有用并想支持其开发，可以考虑请我喝杯咖啡！你的支持让项目保持活力，有助于推动新功能的开发。

<div align="center">

### ☕ 请我喝杯咖啡

<a href="https://www.buymeacoffee.com/riteshp2001">
  <img src="https://img.shields.io/badge/请我喝杯咖啡-FFDD00?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black" alt="请我喝杯咖啡">
</a>

### UPI

`panditritesh2001@okhdfcbank`

<a href="upi://pay?pa=panditritesh2001@okhdfcbank&pn=Ritesh%20Pandit&cu=INR">
  <img src="../fastlane/metadata/android/en-US/images/upiqr-code.svg" width="250" height="250" alt="UPI QR Code">
</a>

使用任意 UPI 应用扫码（Google Pay、PhonePe、Paytm、BHIM）

</div>

---

## 维护者发布说明

要通过 Actions 发布签名的 GitHub Release，请配置以下仓库 Secrets：

| Secret 名称 | 描述 |
|---|---|
| `SIGNING_KEYSTORE` | Base64 编码的密钥库文件（`.jks` 或 `.keystore`） |
| `SIGNING_KEY_ALIAS` | 密钥库中的密钥别名 |
| `SIGNING_STORE_PASSWORD` | 密钥库的密码 |
| `KEY_PASSWORD` | 签名密钥的密码 |

然后在 `app/build.gradle.kts` 中更新 `versionCode` 和 `versionName`，创建标签并推送：

```bash
git tag -a v1.3.1 -m "Release version 1.3.1"
git push origin v1.3.1
```

预览版使用相同流程，使用 preview 标签：

```bash
git tag -a v1.3.1-preview.1 -m "Preview release"
git push origin v1.3.1-preview.1
```

---

## 致谢

- [mpv-android](https://github.com/mpv-android)
- [mpvExtended](https://github.com/marlboro-advance/mpvEx)
- [mpvKt](https://github.com/abdallahmehiz/mpvKt)
- [PixelPlayer](https://github.com/theovilardo/PixelPlayer)
- [MpvRex](https://github.com/sfsakhawat999/mpvRex)
- [Next Player](https://github.com/anilbeesetti/nextplayer)
- [Gramophone](https://github.com/FoedusProgramme/Gramophone)
- [hdr-toys](https://github.com/natural-harmonia-gropius/hdr-toys)
- [**SunnyVishnu3**](https://github.com/SunnyVishnu3) 为 `yt-dlp` 原生集成和 SDK 29+ 绕过逻辑做出的贡献

---

## 许可证

基于 **Apache License 2.0** 分发。详见 `LICENSE` 文件。

---

## Star 历史

<a href="https://www.star-history.com/#Riteshp2001/mpvRx&type=date&legend=top-left">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/svg?repos=Riteshp2001/mpvRx&type=date&theme=dark&legend=top-left" />
    <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/svg?repos=Riteshp2001/mpvRx&type=date&legend=top-left" />
    <img alt="Star History Chart" src="https://api.star-history.com/svg?repos=Riteshp2001/mpvRx&type=date&legend=top-left" />
  </picture>
</a>
