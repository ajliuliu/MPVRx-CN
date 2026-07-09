package app.gyrolet.mpvrx.preferences

import androidx.compose.runtime.Composable
import app.gyrolet.mpvrx.ui.icons.AppIcon
import app.gyrolet.mpvrx.ui.icons.Icons

/**
 * Represents a customizable button in the player controls.
 * Now includes an icon for the preference UI.
 */
enum class PlayerButton(
  val icon: AppIcon,
) {
  BACK_ARROW(Icons.Outlined.ArrowBack),
  VIDEO_TITLE(Icons.Outlined.Title),
  BOOKMARKS_CHAPTERS(Icons.Outlined.Bookmarks),
  PLAYBACK_SPEED(Icons.Outlined.Speed),
  DECODER(Icons.Default.DeveloperBoard),
  HDR_MODE(Icons.Default.HdrOff),
  SCREEN_ROTATION(Icons.Outlined.ScreenRotation),
  FRAME_NAVIGATION(Icons.Default.Screenshot),
  VIDEO_ZOOM(Icons.Outlined.ZoomIn),
  PICTURE_IN_PICTURE(Icons.Outlined.PictureInPictureAlt),
  ASPECT_RATIO(Icons.Outlined.AspectRatio),
  LOCK_CONTROLS(Icons.Outlined.LockOpen),
  AUDIO_TRACK(Icons.Outlined.Audiotrack),
  SUBTITLES(Icons.Outlined.Subtitles),
  MORE_OPTIONS(Icons.Outlined.MoreVert),
  CURRENT_CHAPTER(Icons.Outlined.Bookmarks), // <-- CHANGED ICON
  REPEAT_MODE(Icons.Filled.Repeat),
  SHUFFLE(Icons.Outlined.Shuffle),
  MIRROR(Icons.Outlined.Flip),
  VERTICAL_FLIP(Icons.Outlined.Flip),
  AB_LOOP(Icons.Outlined.Repeat),
  CUSTOM_SKIP(Icons.Outlined.FastForward),
  BACKGROUND_PLAYBACK(Icons.Outlined.Headset),
  AMBIENT_MODE(Icons.Outlined.BlurOff),
  TIME_NETWORK(Icons.Default.AccessTime),
  NONE(Icons.Outlined.Bookmarks),
}

/**
 * A list of all buttons that the user can choose from in the customization menu.
 * Excludes NONE (placeholder) and constant buttons (BACK_ARROW, VIDEO_TITLE).
 */
val allPlayerButtons =
  PlayerButton.values().filter {
    it != PlayerButton.NONE &&
      it != PlayerButton.BACK_ARROW &&
      it != PlayerButton.VIDEO_TITLE
  }

/**
 * Gets the human-readable label for a player button.
 * TODO: You must add these string resources to your `strings.xml` file.
 */
@Composable
fun getPlayerButtonLabel(button: PlayerButton): String =
  when (button) {
    PlayerButton.BACK_ARROW -> "返回箭头"
    PlayerButton.VIDEO_TITLE -> "视频标题"
    PlayerButton.BOOKMARKS_CHAPTERS -> "章节 / 书签"
    PlayerButton.PLAYBACK_SPEED -> "播放速度"
    PlayerButton.DECODER -> "解码器"
    PlayerButton.HDR_MODE -> "HDR 屏幕输出"
    PlayerButton.SCREEN_ROTATION -> "屏幕旋转"
    PlayerButton.FRAME_NAVIGATION -> "逐帧导航"
    PlayerButton.VIDEO_ZOOM -> "视频缩放"
    PlayerButton.PICTURE_IN_PICTURE -> "画中画"
    PlayerButton.ASPECT_RATIO -> "宽高比"
    PlayerButton.LOCK_CONTROLS -> "锁定控制项"
    PlayerButton.AUDIO_TRACK -> "音频轨道"
    PlayerButton.SUBTITLES -> "字幕"
    PlayerButton.MORE_OPTIONS -> "更多选项"
    PlayerButton.CURRENT_CHAPTER -> "当前章节"
    PlayerButton.REPEAT_MODE -> "循环模式"
    PlayerButton.SHUFFLE -> "随机播放"
    PlayerButton.MIRROR -> "水平翻转"
    PlayerButton.VERTICAL_FLIP -> "垂直翻转"
    PlayerButton.AB_LOOP -> "A-B 循环"
    PlayerButton.CUSTOM_SKIP -> "自定义跳过"
    PlayerButton.BACKGROUND_PLAYBACK -> "后台播放"
    PlayerButton.AMBIENT_MODE -> "氛围模式"
    PlayerButton.TIME_NETWORK -> "时间 + 网络"
    PlayerButton.NONE -> "无"
  }
