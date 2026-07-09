# MpvRx Lua 与 JavaScript 命令指南

本文档记录了 MpvRx 向 mpv Lua 和 JavaScript 脚本公开的所有脚本接口。

脚本通过向以下属性写入字符串值与 MpvRx 通信：

```text
user-data/mpvrx/*
```

MpvRx 会监听这些属性，执行对应的原生播放器操作，并在处理后清除命令属性。唯一的例外是 curl 桥接：`curl_request` 和 `curl_response` 会保留足够长的时间以完成异步 HTTP 处理。

以下示例使用了公开的无认证 API 端点：

- JSONPlaceholder: https://jsonplaceholder.typicode.com/
- httpbin: https://httpbin.org/

## 快速开始

1. 在 MpvRx 高级设置中启用 Lua/JS 脚本功能。
2. 将脚本文件放入已选择的 mpv 配置文件夹中。
3. 建议使用 `scripts/` 子文件夹。MpvRx 也会回退到配置根目录。
4. Lua 脚本使用 `.lua` 扩展名，JavaScript 脚本使用 `.js` 扩展名。
5. 在 MpvRx 脚本面板中选择要启用的脚本。
6. 如果在播放开始前添加了脚本，需要重新打开视频。

MpvRx 也会同步已选 mpv 配置文件夹中的 `script-opts/` 目录。

## 重要规则

- 所有 MpvRx 命令属性均以字符串形式处理。
- 跳转值必须为整数秒，不要发送小数。
- `seek_to_with_text` 和 `seek_by_with_text` 使用 `秒数|消息` 格式。
- `curl_request` 是异步的，务必监听 `curl_response`。
- 始终为 curl 请求指定唯一的 `id`，并忽略具有不同 `id` 的响应。
- JavaScript 通过 mpv 的 JavaScript 运行执行，使用 ES5 兼容语法最为安全：推荐使用 `var` 和 `function`。

## 支持的命令

| 属性 | 值 | 功能说明 |
| --- | --- | --- |
| `user-data/mpvrx/show_text` | 任意字符串 | 显示原生 MpvRx 文本叠加层。 |
| `user-data/mpvrx/toggle_ui` | `show`、`hide`、`toggle` | 显示、隐藏或切换播放器控件。 |
| `user-data/mpvrx/show_panel` | 面板 ID | 打开原生 MpvRx 面板或工作表。 |
| `user-data/mpvrx/seek_to` | 整数秒 | 跳转到绝对时间戳。 |
| `user-data/mpvrx/seek_by` | 整数秒 | 相对于当前时间戳跳转。 |
| `user-data/mpvrx/seek_to_with_text` | `秒数|消息` | 绝对跳转并显示叠加文字。 |
| `user-data/mpvrx/seek_by_with_text` | `秒数|消息` | 相对跳转并显示叠加文字。 |
| `user-data/mpvrx/software_keyboard` | `show`、`hide`、`toggle` | 控制 Android 软键盘。 |
| `user-data/mpvrx/curl_request` | JSON 字符串 | 通过原生 curl 运行异步 HTTP 请求。 |
| `user-data/mpvrx/curl_response` | JSON 字符串 | MpvRx 写入的响应，脚本应监听此属性。 |

支持的面板 ID：

| 面板 ID | 效果 |
| --- | --- |
| `frame_navigation` | 打开逐帧导航面板。 |
| `subtitle_settings` | 打开字幕样式设置。 |
| `subtitle_delay` | 打开字幕延迟控制。 |
| `audio_delay` | 打开音频延迟控制。 |
| `video_filters` | 打开视频滤镜控制。 |
| `lua_scripts` | 打开脚本面板。 |
| `hdr_screen_output` | 打开 HDR 屏幕输出控制。 |

已监听但未公开的命令：

`set_button_title`、`reset_button_title` 和 `toggle_button` 目前已被 mpv 属性观察器监听，但播放器命令分派器尚未为其实现公开行为，请将其视为保留命令。

## 完整 Lua 示例

将此文件保存为 mpv `scripts/` 文件夹中的 `mpvrx_demo.lua`。

该脚本会从 JSONPlaceholder 获取一篇公开帖子，显示帖子标题，并包含常用 MpvRx 命令的按键绑定。

```lua
-- mpvrx_demo.lua

local utils = require("mp.utils")

local REQUEST_ID = "mpvrx-demo-lua-post"

local function mpvrx(command, value)
    mp.set_property("user-data/mpvrx/" .. command, tostring(value))
end

local function show(message)
    mpvrx("show_text", message)
end

local function fetch_post()
    show("正在从 JSONPlaceholder 获取帖子...")

    mpvrx("curl_request", utils.format_json({
        id = REQUEST_ID,
        url = "https://jsonplaceholder.typicode.com/posts/1",
        method = "GET",
        headers = {
            Accept = "application/json",
        },
        timeout = 15,
    }))
end

mp.observe_property("user-data/mpvrx/curl_response", "string", function(_, value)
    if value == nil or value == "" then return end

    local res = utils.parse_json(value)
    if res == nil or res.id ~= REQUEST_ID then return end

    if res.error ~= nil then
        show("Curl 失败: " .. tostring(res.error))
        return
    end

    if tonumber(res.status) ~= 200 then
        show("HTTP " .. tostring(res.status))
        return
    end

    local body = utils.parse_json(res.body)
    if body == nil then
        show("无法解析响应正文")
        return
    end

    show("帖子 #" .. tostring(body.id) .. "\n" .. tostring(body.title))
end)

mp.register_event("file-loaded", fetch_post)

mp.add_key_binding("J", "mpvrx-fetch-jsonplaceholder-post", fetch_post)
mp.add_key_binding("U", "mpvrx-toggle-ui", function()
    mpvrx("toggle_ui", "toggle")
end)
mp.add_key_binding("V", "mpvrx-open-video-filters", function()
    mpvrx("show_panel", "video_filters")
end)
mp.add_key_binding("RIGHT", "mpvrx-seek-forward", function()
    mpvrx("seek_by_with_text", "30|前进 30 秒")
end)
mp.add_key_binding("LEFT", "mpvrx-seek-back", function()
    mpvrx("seek_by_with_text", "-10|后退 10 秒")
end)
```

## 完整 JavaScript 示例

将此文件保存为 mpv `scripts/` 文件夹中的 `mpvrx_demo.js`。

此示例使用 ES5 风格的 JavaScript 以确保 mpv 兼容性。它向 JSONPlaceholder 发送 POST 请求，并显示公开测试 API 返回的假创建帖子 ID。

```javascript
// mpvrx_demo.js

var REQUEST_ID = "mpvrx-demo-js-create-post";

function mpvrx(command, value) {
    mp.set_property("user-data/mpvrx/" + command, String(value));
}

function show(message) {
    mpvrx("show_text", message);
}

function createPost() {
    var payload = {
        title: "MpvRx JavaScript curl 测试",
        body: "通过 MpvRx curl 从 mpv JavaScript 脚本发送。",
        userId: 1
    };

    show("正在向 JSONPlaceholder 发送...");

    mpvrx("curl_request", JSON.stringify({
        id: REQUEST_ID,
        url: "https://jsonplaceholder.typicode.com/posts",
        method: "POST",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload),
        content_type: "application/json",
        timeout: 15
    }));
}

mp.observe_property("user-data/mpvrx/curl_response", "string", function(name, value) {
    if (!value) return;

    var res;
    try {
        res = JSON.parse(value);
    } catch (e) {
        return;
    }

    if (!res || res.id !== REQUEST_ID) return;

    if (res.error) {
        show("Curl 失败: " + res.error);
        return;
    }

    if (res.status < 200 || res.status >= 300) {
        show("HTTP " + res.status);
        return;
    }

    var body;
    try {
        body = JSON.parse(res.body);
    } catch (e2) {
        show("无法解析响应正文");
        return;
    }

    show("已创建假帖子 #" + body.id + "\nHTTP " + res.status);
});

mp.add_key_binding("P", "mpvrx-create-jsonplaceholder-post", createPost);
mp.add_key_binding("U", "mpvrx-toggle-ui-js", function() {
    mpvrx("toggle_ui", "toggle");
});
mp.add_key_binding("S", "mpvrx-open-subtitle-settings-js", function() {
    mpvrx("show_panel", "subtitle_settings");
});
mp.add_key_binding("K", "mpvrx-show-keyboard-js", function() {
    mpvrx("software_keyboard", "show");
});
```
