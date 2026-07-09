package app.gyrolet.mpvrx.ui.preferences

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.gyrolet.mpvrx.R
import app.gyrolet.mpvrx.preferences.YtdlPreferences
import app.gyrolet.mpvrx.preferences.preference.collectAsState
import app.gyrolet.mpvrx.presentation.Screen
import app.gyrolet.mpvrx.ui.icons.Icon
import app.gyrolet.mpvrx.ui.icons.Icons
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlCodecPreference
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlContainerPreference
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlHdrPreference
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlPlaylistMode
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlAudioPreference
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlpManager
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlpOptionSettings
import app.gyrolet.mpvrx.ui.player.ytdlp.YtdlpOptionsBuilder
import androidx.compose.runtime.saveable.rememberSaveable
import app.gyrolet.mpvrx.ui.theme.spacing
import app.gyrolet.mpvrx.ui.utils.LocalBackStack
import app.gyrolet.mpvrx.ui.utils.popSafely
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject
import java.io.File
import me.zhanghai.compose.preference.ProvidePreferenceLocals
import app.gyrolet.mpvrx.ui.preferences.components.SwitchPreference

@Serializable
object YtdlpSettingsScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val backStack = LocalBackStack.current
        val scope = rememberCoroutineScope()
        val scrollState = rememberScrollState()
        var isRunning by remember { mutableStateOf(false) }

        val ytdlPreferences = koinInject<YtdlPreferences>()
        val ytdlQuality by ytdlPreferences.ytdlQuality.collectAsState()
        val preferH264 by ytdlPreferences.preferH264.collectAsState()
        val codecPreference by ytdlPreferences.codecPreference.collectAsState()
        val maxFps by ytdlPreferences.maxFps.collectAsState()
        val hdrPreference by ytdlPreferences.hdrPreference.collectAsState()
        val containerPreference by ytdlPreferences.containerPreference.collectAsState()
        val audioPreference by ytdlPreferences.audioPreference.collectAsState()
        val playlistMode by ytdlPreferences.playlistMode.collectAsState()
        val geoBypass by ytdlPreferences.geoBypass.collectAsState()
        val liveFromStart by ytdlPreferences.liveFromStart.collectAsState()
        val writeSubs by ytdlPreferences.writeSubs.collectAsState()
        val writeAutoSubs by ytdlPreferences.writeAutoSubs.collectAsState()
        
        var showAdvancedNetworking by rememberSaveable { mutableStateOf(false) }
        
        var userAgentText by remember { mutableStateOf(ytdlPreferences.customUserAgent.get()) }
        var subtitleLanguagesText by remember { mutableStateOf(ytdlPreferences.subtitleLanguages.get()) }
        var formatSortText by remember { mutableStateOf(ytdlPreferences.formatSort.get()) }
        var mergeOutputFormatText by remember { mutableStateOf(ytdlPreferences.mergeOutputFormat.get()) }
        var refererText by remember { mutableStateOf(ytdlPreferences.referer.get()) }
        var cookiesFileText by remember { mutableStateOf(ytdlPreferences.cookiesFile.get()) }
        var proxyText by remember { mutableStateOf(ytdlPreferences.proxy.get()) }
        var extractorArgsText by remember { mutableStateOf(ytdlPreferences.extractorArgs.get()) }
        var sponsorBlockMarkText by remember { mutableStateOf(ytdlPreferences.sponsorBlockMark.get()) }
        var sponsorBlockRemoveText by remember { mutableStateOf(ytdlPreferences.sponsorBlockRemove.get()) }
        var rawOptionsText by remember { mutableStateOf(ytdlPreferences.customRawOptions.get()) }

        val ytdlDir = remember { YtdlpManager.getYtdlDir(context) }
        var hasYtdlp by remember { mutableStateOf(File(ytdlDir, "yt-dlp").exists()) }

        LaunchedEffect(isRunning) {
            if (!isRunning) {
                hasYtdlp = File(ytdlDir, "yt-dlp").exists()
            }
        }

        val ytdlpInfo = remember(hasYtdlp) {
            if (hasYtdlp) {
                val size = try {
                    val f = File(ytdlDir, "yt-dlp")
                    if (f.exists()) " (${f.length() / 1024 / 1024} MB)" else ""
                } catch (_: Exception) { "" }
                "已安装$size"
            } else {
                "未配置"
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "yt-dlp 流媒体",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { backStack.popSafely() }) {
                            Icon(Icons.Outlined.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        ) { padding ->
            ProvidePreferenceLocals {
                Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
            ) {
                // Expressive Installation Status Card
                PreferenceCard {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (hasYtdlp) MaterialTheme.colorScheme.primaryContainer 
                                    else MaterialTheme.colorScheme.errorContainer,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (hasYtdlp) Icons.Default.Check else Icons.Default.CloudDownload,
                                    contentDescription = null,
                                    tint = if (hasYtdlp) MaterialTheme.colorScheme.onPrimaryContainer 
                                           else MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "yt-dlp 核心引擎",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.height(2.dp))
                            Text(
                                text = if (hasYtdlp) "子进程已激活，可用于流媒体播放" 
                                       else "引擎缺失，请运行下方安装程序。",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (hasYtdlp) MaterialTheme.colorScheme.primary 
                                    else MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(start = 4.dp)
                        ) {
                            Text(
                                text = ytdlpInfo,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (hasYtdlp) MaterialTheme.colorScheme.onPrimary 
                                        else MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                }

                PreferenceSectionHeader(title = "引擎安装器")

                PreferenceCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "管理 yt-dlp 环境",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "下载最新的封装模块并在本地沙盒文件夹内编译 Python 原生二进制文件。",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        isRunning = true
                                        YtdlpManager.runInstall(context) {}
                                        isRunning = false
                                    }
                                },
                                enabled = !isRunning,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.onPrimary
                                )
                            ) {
                                Icon(Icons.Default.Download, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("安装核心")
                            }

                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        isRunning = true
                                        YtdlpManager.runUpdate(context) {}
                                        isRunning = false
                                    }
                                },
                                enabled = !isRunning && hasYtdlp,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                ),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                            ) {
                                Icon(Icons.Default.Update, null, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("更新核心")
                            }
                        }

                        Spacer(Modifier.height(4.dp))

                        OutlinedButton(
                            onClick = {
                                scope.launch {
                                    isRunning = true
                                    YtdlpManager.runUpdateToNightly(context) {}
                                    isRunning = false
                                }
                            },
                            enabled = !isRunning && hasYtdlp,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.secondary
                            ),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f))
                        ) {
                            Icon(Icons.Default.Update, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("更新到每日构建版")
                        }
                    }
                }

                PreferenceSectionHeader(title = "质量与格式")

                PreferenceCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        Text(
                            text = "流媒体质量",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        
                        val qualityLevels = remember { arrayOf(-1, 2160, 1440, 1080, 720, 480, 360, 240, 144) }
                        val qualityLabels = remember { qualityLevels.map { if (it == -1) "不限" else "${it}p" } }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            qualityLevels.forEachIndexed { index, level ->
                                FilterChip(
                                    selected = ytdlQuality == level,
                                    onClick = {
                                        ytdlPreferences.ytdlQuality.set(level)
                                        updateFormatString(ytdlPreferences)
                                    },
                                    label = { Text(qualityLabels[index]) },
                                    leadingIcon = if (ytdlQuality == level) {
                                        { Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp)) }
                                    } else null,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }

                        PreferenceDivider()

                        OptionDropdown(
                            title = "视频编码",
                            value = codecPreference,
                            values = YtdlCodecPreference.entries,
                            valueLabel = { it.title },
                            onValueChange = { selected ->
                                ytdlPreferences.codecPreference.set(selected)
                                ytdlPreferences.preferH264.set(selected == YtdlCodecPreference.H264)
                                updateFormatString(ytdlPreferences)
                            },
                        )

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf(0 to "不限帧率", 30 to "30 FPS", 60 to "60 FPS", 120 to "120 FPS").forEach { (fps, label) ->
                                FilterChip(
                                    selected = maxFps == fps,
                                    onClick = {
                                        ytdlPreferences.maxFps.set(fps)
                                        updateFormatString(ytdlPreferences)
                                    },
                                    label = { Text(label) },
                                    leadingIcon = if (maxFps == fps) {
                                        { Icon(Icons.Default.Check, null, modifier = Modifier.size(16.dp)) }
                                    } else null,
                                    shape = RoundedCornerShape(12.dp)
                                )
                            }
                        }

                        OptionDropdown(
                            title = "HDR 偏好",
                            value = hdrPreference,
                            values = YtdlHdrPreference.entries,
                            valueLabel = { it.title },
                            onValueChange = {
                                ytdlPreferences.hdrPreference.set(it)
                                updateFormatString(ytdlPreferences)
                            },
                        )

                        OptionDropdown(
                            title = "容器格式",
                            value = containerPreference,
                            values = YtdlContainerPreference.entries,
                            valueLabel = { it.title },
                            onValueChange = {
                                ytdlPreferences.containerPreference.set(it)
                                updateFormatString(ytdlPreferences)
                            },
                        )

                        OptionDropdown(
                            title = "音频偏好",
                            value = audioPreference,
                            values = YtdlAudioPreference.entries,
                            valueLabel = { it.title },
                            onValueChange = { selected ->
                                ytdlPreferences.audioPreference.set(selected)
                                updateFormatString(ytdlPreferences)
                            },
                        )


                        PreferenceDivider()

                        val currentFormat = remember(ytdlQuality, preferH264, codecPreference, maxFps, hdrPreference, containerPreference, audioPreference) {
                            YtdlpOptionsBuilder.buildFormat(YtdlpOptionSettings.fromYtdlPreferences(ytdlPreferences))
                        }
                        
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = "生成的格式字符串",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = currentFormat.ifBlank { "(默认)" },
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp
                                    ),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                PreferenceSectionHeader(title = "字幕与语言")

                PreferenceCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        SwitchPreference(
                            value = writeSubs,
                            onValueChange = { ytdlPreferences.writeSubs.set(it) },
                            title = { Text("下载媒体字幕", fontWeight = FontWeight.Medium) },
                            summary = { Text("自动从支持的 URL 提取并加载实体字幕轨道。") }
                        )

                        PreferenceDivider()

                        SwitchPreference(
                            value = writeAutoSubs,
                            onValueChange = { ytdlPreferences.writeAutoSubs.set(it) },
                            title = { Text("包含自动生成的字幕", fontWeight = FontWeight.Medium) },
                            summary = { Text("当常规字幕不存在时，获取自动字幕轨道（如 YouTube 语音转文本）。") }
                        )

                        PreferenceDivider()

                        OutlinedTextField(
                            value = subtitleLanguagesText,
                            onValueChange = {
                                subtitleLanguagesText = it
                                ytdlPreferences.subtitleLanguages.set(it)
                            },
                            label = { Text("字幕语言") },
                            placeholder = { Text("all 或 en.*,ja") },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth(),
                            supportingText = { Text("仅针对 yt-dlp 下载覆盖应用字幕语言设置。") }
                        )
                    }
                }

                PreferenceSectionHeader(title = "高级网络")

                PreferenceCard {
                    Column(
                        modifier = Modifier.fillMaxWidth().animateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showAdvancedNetworking = !showAdvancedNetworking }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "高级配置",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "自定义 HTTP 代理、提取器参数、SponsorBlock 和原始选项",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                imageVector = if (showAdvancedNetworking) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        
                        if (showAdvancedNetworking) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .padding(bottom = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
                            ) {
                                PreferenceDivider()

                                OutlinedTextField(
                                    value = formatSortText,
                                    onValueChange = {
                                        formatSortText = it
                                        ytdlPreferences.formatSort.set(it)
                                    },
                                    label = { Text("格式排序") },
                                    placeholder = { Text("res,fps,hdr:12,vcodec:vp9.2") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    supportingText = { Text("传递给 yt-dlp 的 format-sort 参数，用于高级排序。") }
                                )

                                PreferenceDivider()

                                OutlinedTextField(
                                    value = mergeOutputFormatText,
                                    onValueChange = {
                                        mergeOutputFormatText = it
                                        ytdlPreferences.mergeOutputFormat.set(it)
                                    },
                                    label = { Text("合并输出格式") },
                                    placeholder = { Text("mp4, mkv, webm") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                )
                                
                                PreferenceDivider()
                                
                                OutlinedTextField(
                                    value = userAgentText,
                                    onValueChange = { 
                                        userAgentText = it
                                        ytdlPreferences.customUserAgent.set(it)
                                    },
                                    label = { Text("自定义 User-Agent 覆盖") },
                                    placeholder = { Text("Mozilla/5.0 ...") },
                                    singleLine = false,
                                    maxLines = 3,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                                    ),
                                    supportingText = {
                                        Text("留空使用默认浏览器 User-Agent。有助于绕过反爬虫检测。")
                                    }
                                )

                                PreferenceDivider()

                                OutlinedTextField(
                                    value = refererText,
                                    onValueChange = {
                                        refererText = it
                                        ytdlPreferences.referer.set(it)
                                    },
                                    label = { Text("Referer") },
                                    placeholder = { Text("https://www.youtube.com/") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                )

                                OutlinedTextField(
                                    value = cookiesFileText,
                                    onValueChange = {
                                        cookiesFileText = it
                                        ytdlPreferences.cookiesFile.set(it)
                                    },
                                    label = { Text("Cookie 文件") },
                                    placeholder = { Text("/storage/emulated/0/Download/cookies.txt") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                )

                                OutlinedTextField(
                                    value = proxyText,
                                    onValueChange = {
                                        proxyText = it
                                        ytdlPreferences.proxy.set(it)
                                    },
                                    label = { Text("代理") },
                                    placeholder = { Text("socks5://127.0.0.1:1080") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                )

                                OutlinedTextField(
                                    value = extractorArgsText,
                                    onValueChange = {
                                        extractorArgsText = it
                                        ytdlPreferences.extractorArgs.set(it)
                                    },
                                    label = { Text("提取器参数") },
                                    placeholder = { Text("youtube:player_client=android,web") },
                                    singleLine = false,
                                    maxLines = 2,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                )

                                OptionDropdown(
                                    title = "播放列表行为",
                                    value = playlistMode,
                                    values = YtdlPlaylistMode.entries,
                                    valueLabel = { it.title },
                                    onValueChange = { ytdlPreferences.playlistMode.set(it) },
                                )

                                SwitchPreference(
                                    value = geoBypass,
                                    onValueChange = { ytdlPreferences.geoBypass.set(it) },
                                    title = { Text("地理位置绕过", fontWeight = FontWeight.Medium) },
                                    summary = { Text("要求 yt-dlp 使用提取器级别的区域绕过逻辑。") }
                                )

                                SwitchPreference(
                                    value = liveFromStart,
                                    onValueChange = { ytdlPreferences.liveFromStart.set(it) },
                                    title = { Text("从头播放直播", fontWeight = FontWeight.Medium) },
                                    summary = { Text("当提取器支持时，从头开始播放直播流。") }
                                )

                                OutlinedTextField(
                                    value = sponsorBlockMarkText,
                                    onValueChange = {
                                        sponsorBlockMarkText = it
                                        ytdlPreferences.sponsorBlockMark.set(it)
                                    },
                                    label = { Text("SponsorBlock 标记") },
                                    placeholder = { Text("sponsor,selfpromo") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                )

                                OutlinedTextField(
                                    value = sponsorBlockRemoveText,
                                    onValueChange = {
                                        sponsorBlockRemoveText = it
                                        ytdlPreferences.sponsorBlockRemove.set(it)
                                    },
                                    label = { Text("SponsorBlock 移除") },
                                    placeholder = { Text("sponsor") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                )

                                PreferenceDivider()

                                OutlinedTextField(
                                    value = rawOptionsText,
                                    onValueChange = { 
                                        rawOptionsText = it
                                        ytdlPreferences.customRawOptions.set(it)
                                    },
                                    label = { Text("原始 yt-dlp 选项") },
                                    placeholder = { Text("extractor-args=\"youtube:player_client=android,web\"\ngeo-bypass=") },
                                    singleLine = false,
                                    maxLines = 6,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                                    ),
                                    supportingText = {
                                        Text("以上未涵盖的选项。用换行符或逗号分隔选项；包含逗号的值需加引号。")
                                    }
                                )
                            }
                        }
                    }
                }

                // Engine Installer moved to top
            }
        }
    }
    }

    private fun updateFormatString(prefs: YtdlPreferences) {
        prefs.ytdlFormat.set(YtdlpOptionsBuilder.buildFormat(YtdlpOptionSettings.fromYtdlPreferences(prefs)))
    }
}

private fun YtdlpOptionSettings.Companion.fromYtdlPreferences(prefs: YtdlPreferences): YtdlpOptionSettings =
    YtdlpOptionSettings(
        codecPreference = prefs.codecPreference.get(),
        legacyPreferH264 = prefs.preferH264.get(),
        maxHeight = prefs.ytdlQuality.get(),
        maxFps = prefs.maxFps.get(),
        hdrPreference = prefs.hdrPreference.get(),
        containerPreference = prefs.containerPreference.get(),
        audioPreference = prefs.audioPreference.get(),
    )

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun <T> OptionDropdown(
    title: String,
    value: T,
    values: List<T>,
    valueLabel: (T) -> String,
    onValueChange: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            value = valueLabel(value),
            onValueChange = {},
            readOnly = true,
            label = { Text(title) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            values.forEach { item ->
                DropdownMenuItem(
                    text = { Text(valueLabel(item)) },
                    onClick = {
                        expanded = false
                        onValueChange(item)
                    },
                )
            }
        }
    }
}
