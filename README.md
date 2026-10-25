# AI 产品经理求职手账 · Android 版

《AI 产品经理求职指南｜从技术到产品》的安卓原生 App（Kotlin + Jetpack Compose），内容与网页版完全一致，全部离线可用。

## 内容

- 首页：封面、公式链、核心思想、定位 / 作品 / 面试路线、七章索引、收尾金句
- 方向组：01 核心结论（卡片 + 单页 + 上一篇 / 下一篇）、02 能力模型（能力选择器 + 单页）
- 方法组：03 产品思维（估算题步骤 + 估算实验室：4 个滑杆实时算需求侧 / 供给侧区间）、05 工作闭环（步骤选择 + 单页）
- 执行组：04 简历策略（可展开抽屉 + 描述链条）、06 行动清单（勾选进度，保存在本机）、07 补充要点（贴士卡 + 单页）

## 构建

Debug APK（无需签名，可直接安装）：

```bash
./gradlew :app:assembleDebug
# 产物：app/build/outputs/apk/debug/app-debug.apk
```

GitHub Actions 会在每次推送到 main 时构建，并把 APK 发布到 Release（见 `.github/workflows/build.yml`）。

## 技术

- Kotlin 2.0.21 / AGP 8.7.3 / Gradle 8.11.1
- compileSdk / targetSdk 35，minSdk 26
- Compose BOM 2024.12.01 + Material 3 + Navigation Compose
- 主 Logo 为 TP 的立体线条图（`app/src/main/res/drawable-nodpi/logo.png`）
