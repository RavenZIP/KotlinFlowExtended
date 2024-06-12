# KotlinFlowExtended

<p align="center">
      <img alt="Logo" src="https://github.com/RavenZIP/KotlinFlowExtended/assets/131264945/00c53ddc-1c7f-4c46-b21b-4d913546b648" width="100%">
</p>

<p align="center">
<img alt="Android" src="https://img.shields.io/badge/Android-39ad31">
<img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-1.9.10-A831F5">
<img alt="Static Badge" src="https://img.shields.io/badge/v1.0.0-f88909">
</p>

# 📄 О проекте
## Описание
Данная библиотека создана с целью расширить возможности Kotlin Flow дополнительными функциями

# 🛠 Установка
В файл **settings.gradle.kts** добавьте
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven ("https://jitpack.io")
    }
}
```
Затем, в файл **build.gradle.kts (:app)** добавьте
```
dependencies {
      implementation("com.github.RavenZIP:KotlinFlowExtended:1.0.1")
}
```
Обратите внимание, что minSdk должен быть указан >= 27
```
android {
      defaultConfig {
            minSdk = 27
      }
}
```

Синхронизируйте Gradle с проектом и запустите сборку.

# 👾 Разработчик
**Черных Александр**
- [Github](https://github.com/RavenZIP)
- [Telegram](https://t.me/bexwdgst)
- [Telegram канал с проектами](https://t.me/RavenZIProjects)
