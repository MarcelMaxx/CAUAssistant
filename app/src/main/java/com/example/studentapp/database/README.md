# 数据库模块维护文档

## 1. 模块概述

本数据库模块使用Room持久化库实现，主要用于管理学生应用的各类数据，包括校园公告、食堂菜单、教授信息等。模块采用MVVM架构，遵循Android最佳实践。

### 1.1 核心功能

- 数据持久化存储
- JSON数据导入
- 数据查询接口
- 数据更新机制

### 1.2 技术栈

- Room：Android官方推荐的本地数据库解决方案
- Kotlin协程：处理异步操作
- Moshi：JSON解析库
- Flow：响应式数据流

## 2. 目录结构

```
com.example.studentapp.database/
├── entity/                 # 数据库实体类
├── dao/                    # 数据访问对象
├── repository/            # 数据仓库
├── AppDatabase.kt        # 数据库主类
└── README.md             # 本文档
```

## 3. 数据库表结构

### 3.1 校园公告表 (campus_notification)
- link (TEXT, PRIMARY KEY)
- title (TEXT)
- date (TEXT)

### 3.2 食堂菜单表 (canteen_menu)
- date (TEXT, PRIMARY KEY)
- canteen (TEXT, PRIMARY KEY)
- time (TEXT)
- meal_type (TEXT, PRIMARY KEY)
- price (TEXT)

[其他表结构...]

## 4. 如何维护数据库

### 4.1 添加新表

1. 在`entity`目录下创建新的实体类
2. 在`dao`目录下创建对应的DAO接口
3. 在`AppDatabase`类中：
   - 添加实体到`@Database`注解
   - 增加版本号
   - 添加DAO的抽象获取方法
4. 在`DatabaseRepository`中添加相关操作方法

### 4.2 修改现有表

1. 修改对应的实体类
2. 增加数据库版本号
3. 创建迁移策略（Migration）

### 4.3 添加新的查询方法

1. 在相应的DAO接口中添加新的查询方法
2. 在`DatabaseRepository`中添加对应的封装方法

## 5. JSON数据导入

### 5.1 数据文件位置
所有JSON文件存储在`com.example.database.data`目录下：
- CampusNotification.json
- EmptyClassroom.json
- CanteenMenu.json
- Professor.json
- HomeworkNotification.json
- CoursesNotification.json
- ClassSchedule.json

### 5.2 导入流程
1. 读取JSON文件
2. 使用Moshi解析数据
3. 通过Repository导入到数据库

## 6. 最佳实践

### 6.1 数据库操作
- 所有数据库操作都应该在后台线程执行
- 使用协程处理异步操作
- 使用Flow实现响应式数据流
- 避免在主线程进行数据库操作

### 6.2 错误处理
- 使用try-catch捕获异常
- 记录详细的错误日志
- 实现数据回滚机制

### 6.3 性能优化
- 使用索引优化查询性能
- 批量操作使用事务
- 避免大量数据的频繁读写

## 7. 常见问题解决

### 7.1 数据库升级失败
1. 检查版本号是否正确增加
2. 确认是否提供了正确的迁移策略
3. 如果数据不重要，可以使用`fallbackToDestructiveMigration()`

### 7.2 数据导入失败
1. 检查JSON文件格式是否正确
2. 确认文件路径是否正确
3. 查看是否有足够的存储空间

## 8. 联系方式

如有问题，请联系：virzyr7@gmail.com

## 9. 版本历史

- v1.0.0 (2024-12-09)
  - 初始版本
  - 实现基本的数据库功能
  - 支持JSON数据导入

## 10. StudentApp 数据库模块使用指南

### 10.1 模块概述

本模块使用 Room 数据库框架实现数据持久化，提供了对多个实体的增删改查操作。

### 10.2 数据库架构

#### 10.2.1 实体类列表

- `CampusNotification`：校园公告
- `CanteenMenu`：食堂菜单
- `Professor`：教授信息
- `HomeworkNotification`：作业通知
- `CourseNotification`：课程公告
- `Schedule`：课程表

#### 10.2.2 数据访问对象（DAO）

每个实体都有对应的 DAO，提供基本的数据库操作：

- `CampusNotificationDao`
- `CanteenMenuDao`
- `ProfessorDao`
- `HomeworkNotificationDao`
- `CourseNotificationDao`
- `ScheduleDao`

### 10.3 数据库操作

#### 10.3.1 获取数据库实例

```kotlin
val database = AppDatabase.getDatabase(context)
```

#### 10.3.2 获取 DAO

```kotlin
val campusNotificationDao = database.campusNotificationDao()
val canteenMenuDao = database.canteenMenuDao()
// 其他 DAO 类似
```

#### 10.3.3 基本操作示例

##### 插入数据

```kotlin
// 单个插入
campusNotificationDao.insert(notification)

// 批量插入
campusNotificationDao.insertAll(notificationList)
```

##### 查询数据

```kotlin
// 获取所有数据
val allNotifications = campusNotificationDao.getAllNotifications()

// 使用 Flow 监听数据变化
campusNotificationDao.getAllNotifications().collect { notifications ->
    // 处理通知列表
}
```

### 10.4 数据库维护指南

#### 10.4.1 添加新表

1. 创建实体类（在 `entity` 包下）
2. 创建对应的 DAO 接口（在 `dao` 包下）
3. 在 `AppDatabase` 中添加抽象方法
4. 更新 `DatabaseRepository`

#### 10.4.2 修改现有表

1. 修改实体类定义
2. 更新 DAO 接口
3. 创建数据库迁移策略
4. 更新 `AppDatabase` 的版本号

#### 10.4.3 数据迁移

使用 Room 的迁移机制：

```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // 执行数据库结构变更
    }
}

// 在创建数据库时应用迁移
AppDatabase.getDatabase(context, MIGRATION_1_2)
```

### 10.5 JSON 数据导入

使用 `DatabaseRepository` 导入 JSON 数据：

```kotlin
val repository = DatabaseRepository(context)
val jsonFile = File(context.getExternalFilesDir(null), "sample_data/CampusNotification.json")
repository.importCampusNotifications(jsonFile)
```

### 10.6 性能优化建议

- 尽量使用批量操作
- 在后台线程执行数据库操作
- 使用 Flow 进行响应式编程
- 定期清理不再需要的数据

### 10.7 常见问题排查

- 数据库操作失败：检查日志输出
- 性能问题：使用性能分析工具
- 数据不一致：检查迁移策略和数据导入逻辑

### 10.8 扩展建议

- 考虑添加缓存机制
- 实现更复杂的查询方法
- 添加数据验证逻辑

### 10.9 版本历史

- v1.0：初始实现，支持基本实体和操作
