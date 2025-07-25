# AI模拟面试系统（后端）

---

## 一、项目简介

本项目为“AI模拟面试系统”后端，基于Spring Boot 3.4.6，集成Spring Security、JWT、Mybatis、Spring AI等，提供认证授权、简历解析、AI面试、RBAC权限、数据存储等核心服务。支持Docker容器化部署，敏感信息通过环境变量注入，适合企业级生产环境。

---

## 二、主要功能

- **用户认证与权限管理**：基于RBAC模型，支持JWT、双因素认证、权限细粒度控制。
- **简历管理与智能解析**：简历上传、解析、关键信息提取、评分、格式转换。
- **AI面试引擎**：支持多场景AI问答、面试流程管理、答题评估。
- **企业与岗位管理**：企业、岗位、用户、角色、权限等基础数据管理。
- **统一响应与异常处理**：标准化API响应结构，丰富的异常处理机制。
- **安全加固**：RSA加密、BCrypt密码、敏感数据脱敏、全链路HTTPS。
- **数据库初始化与测试**：提供完整的DDL/DML脚本及单元测试用例。

---

## 三、目录结构与模块剖析

```
interview-app/
├── src/
│   ├── main/
│   │   ├── java/cn/minglg/interview/
│   │   │   ├── Application.java         # 启动类
│   │   │   ├── auth/                    # 认证与权限模块
│   │   │   │   ├── controller/          # 登录、验证码等接口
│   │   │   │   ├── handler/             # 认证、鉴权、登出等处理器
│   │   │   │   ├── filter/              # JWT、验证码等安全过滤器
│   │   │   │   ├── service/             # 用户、验证码等服务接口与实现
│   │   │   │   ├── mapper/              # MyBatis数据访问层
│   │   │   │   ├── pojo/                # 用户、角色、权限等实体
│   │   │   │   ├── config/              # Spring Security、验证码等配置
│   │   │   │   ├── event/               # 登录成功等事件发布
│   │   │   │   ├── exception/           # 自定义异常
│   │   │   │   ├── wrapper/             # 请求体缓存包装器
│   │   │   ├── common/                  # 通用工具与全局配置
│   │   │   │   ├── constant/            # 常量定义
│   │   │   │   ├── listener/            # 应用监听器
│   │   │   │   ├── properties/          # 全局配置属性
│   │   │   │   ├── response/            # 统一响应结构
│   │   │   │   ├── utils/               # 工具类（JWT、RSA、验证码等）
│   │   │   ├── resume/                  # 简历模块
│   │   │   │   ├── controller/          # 简历相关接口
│   │   │   │   ├── service/             # 简历服务接口与实现
│   ├── resources/
│   │   ├── config/application.yml   # 配置文件，端口8081，敏感信息用环境变量
│   │   ├── mapper/                  # MyBatis XML映射
│   │   ├── init/ddl/                # 数据库表结构SQL
│   │   ├── init/dml/                # 数据库初始化数据SQL
│   ├── test/java/cn/minglg/interview/   # 单元测试
│   │   ├── CommonTest.java, UuidTest.java, utils/
├── Dockerfile                           # 多阶段构建，JDK17，8081端口
├── docker-compose.yml                   # 一键部署示例，环境变量注入
├── pom.xml                              # Maven依赖
└── README.md                            # 项目说明
```

---

## 四、构建与部署

### 4.1 本地构建

```bash
# 1. 构建jar包（跳过测试）
mvn clean package -DskipTests
```

### 4.2 Docker镜像构建与运行

```bash
# 2. 构建镜像
docker build -t interview-app:latest .

# 3. 运行容器（需传递环境变量）
docker run -d \
  -p 8081:8081 \
  -e MYSQL_JDBC_URL=jdbc:mysql://your-mysql-host:3306/yourdb?useSSL=false \
  -e MYSQL_USERNAME=your_db_user \
  -e MYSQL_PASSWORD=your_db_password \
  -e REDIS_HOST=your-redis-host \
  -e REDIS_PASSWORD=your_redis_password \
  --name interview-app interview-app:latest
```

### 4.3 docker-compose一键部署

详见 `docker-compose.yml`，支持多环境变量注入、端口映射、目录挂载等。

---

## 五、配置说明

- **端口**：默认8081（见`application.yml`）
- **敏感信息**：数据库、Redis、MinIO等均通过环境变量注入，安全可靠
- **配置文件**：详见`src/main/resources/config/application.yml`
- **数据库初始化**：
  - 表结构：`src/main/init/ddl/*.sql`
  - 初始化数据：`src/main/init/dml/*.sql`
- **MyBatis映射**：`src/main/resources/mapper/*.xml`

---

## 六、测试

- 单元测试位于 `src/test/java/cn/minglg/interview/`
- 覆盖常用工具类（如JWT、RSA）、通用功能等
- 可用 `mvn test` 执行

---

## 七、后端功能模块详细说明

### 7.1 认证与权限（auth）
- 用户注册、登录、登出、验证码校验
- JWT签发与校验、权限拦截、异常处理
- RBAC模型：用户-角色-权限-公司多维绑定

### 7.2 简历管理（resume）
- 简历上传、解析、评分、结构化存储
- 支持多格式（PDF/Word）

### 7.3 通用与全局（common）
- 常量、全局配置、统一响应、工具类
- 应用监听、系统参数、全局异常

### 7.4 数据访问与初始化
- MyBatis多表映射，支持RBAC、简历、企业等核心表
- 提供完整DDL/DML脚本，便于一键初始化

---

## 八、常见问题与建议

- **环境变量未配置**：请确保所有敏感信息均通过环境变量传递，否则服务无法正常启动。
- **端口冲突**：如8081被占用，可在`application.yml`或环境变量中调整。
- **数据库未初始化**：请先执行`init/ddl`和`init/dml`下SQL脚本。
- **日志与上传目录**：如需持久化，可在docker-compose中挂载宿主机目录。

---

## 九、致谢与贡献

如有建议或需求，欢迎提issue或PR！

