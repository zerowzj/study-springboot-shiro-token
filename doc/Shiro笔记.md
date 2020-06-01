

# 一. 

## 1.1 Shiro是什么

​		Apache Shiro是一个强大灵活的开源安全框架，可以完全处理身份验证，授权，企业会话管理和加密。
　　Apache Shiro的首要目标是易于使用和理解。 安全有时可能非常复杂，甚至痛苦，但使用Shiro后就不一定是这样了。 框架应该在可能的情况下掩盖复杂性，并展示一个干净，直观的API，这简化了开发人员工作，并使应用程序更安全。

​		这里有一些可以使用Apache Shiro完全的应用场景

- 验证用户以及验证其身份
- 对用户执行访问控制，例如：在任何环境中使用会话API，即使没有Web或EJB容器
  - 确定用户是否分配了某个安全角色
  - 确定用户是否被允许执行操作
- 在身份验证，访问控制或会话有效期内对事件做出反应
- 聚合一个或多个用户安全数据的数据源，并将此全部显示为单个复合用户的“视图”
- 启用单点登录(SSO)功能
- 为用户关联启用“记住我”服务，无需登录

​       Shiro尝试为所有应用程序环境实现这些目标 - 从最简单的命令行应用程序到最大的企业应用程序，而不强制依赖于其他第三方框架，容器或应用程序服务器。 当然，该项目(Shiro)旨在尽可能地集成到这个Shiro环境中，从而可以在任何环境中想用即用。

## 1.2 Shiro目标

Shiro开发团队称为“应用程序安全的四个基石”，即：认证，授权，会话管理和加密：

- Authentication：身份认证 / 登录，验证用户是不是拥有相应的身份
- Authorization：授权，即权限验证，验证某个已认证的用户是否拥有某个权限；即判断用户是否能做事情，常见的如：验证某个用户是否拥有某个角色。或者细粒度的验证某个用户对某个资源是否具有某个权限；
- Session Manager：会话管理，即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；会话可以是普通 JavaSE 环境的，也可以是如 Web 环境的；
- Cryptography：加密，保护数据的安全性，如密码加密存储到数据库，而不是明文存储；

在不同的应用环境中还有其他特性支持和强化这些问题，特别是：

- Web Support：Web 支持，可以非常容易的集成到 Web 环境；
- Caching：缓存，比如用户登录后，其用户信息、拥有的角色 / 权限不必每次去查，这样可以提高效率；
- Concurrency：并发性，shiro 支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去；
- Testing：测试，存在测试支持，可帮助您编写单元测试和集成测试，并确保代码按预期得到保障；
- Run As：运行方式，允许用户承担另一个用户的身份（如果允许）的功能，有时在管理方案中很有用；
- Remember Me：记住我，这个是非常常见的功能，即一次登录后，下次再来的话不用登录了。

> 记住一点：Shiro 不会去维护用户、维护权限；这些需要我们自己去设计/提供；然后通过相应的接口注入给 Shiro 即可。



# 二. 架构

## 2.1 三个主要概念

- Subject

  ​		主体，当前参与应用安全部分的主体。可以是用户，可以是第三方服务，可以是cron 任务，或者任何东西。主要指一个正在与当前软件交互的东西。所有Subject都需要SecurityManager，当与Subject进行交互，这些交互行为实际上被转换为与SecurityManager的交互。

- SecurityManager

  ​		安全管理员，Shiro架构的核心，它就像Shiro内部所有原件的保护伞。然而一旦配置了SecurityManager，SecurityManager就用到的比较少，开发者大部分时间都花在Subject上面。当你与Subject进行交互的时候，实际上是SecurityManager在背后帮你举起Subject来做一些安全操作。

- Realms

  ​		Realms作为Shiro和应用的连接桥，当需要与安全数据交互的时候，像用户账户，或者访问控制，Shiro就从一个或多个Realms中查找。Shiro提供了一些可以直接使用的Realms，如果默认的Realms不能满足你的需求，你也可以定制自己的Realms。

## 2.2 整体架构

- Subject 

  ​		即主体，外部引用于 Subject 进行交互，Subject 记录了当前操作用户，将用户的概念理解为当前操作的主体，可能是一个通过浏览器请求的用户，也可能是一个运行的程序。Subject 在 Shrio 中是一个接口，接口中定义了很多认证授权相关的方法，外部程序通过 Sbject 进行认证授权，而 Sbject 是通过 SecurityManager 安全管理器进行认证授权的。

- SecurityManager

  ​		即安全管理器，对全部的 Sbject　进行安全管理，它是 Shrio 的核心，通过 SecurityManager 可以完成全部 Sbject 的认证、授权等。实质上 SecurityManager 是通过 Authenticator 进行认证，通过 Authorizer 进行授权，通过 SessionManager 进行会话管理等。SecurityManager是一个接口，继承了 Authenticator、Authorizer、SessionManager 这三个接口。

- Authenticator

  ​		即认证器，对用户身份进行认证，Authenticator 是一个接口，Shrio 提供了 ModularRealmAuthenticator实现类，通过ModularRealmAuthenticator 基本上可以满足大多数需求，也可以自定义认证器。

- Authorizer

  ​		即授权器，用户通过认证器认证通过，在访问功能时需要通过授权器判断用户是否有此功能的操作权限。

- Realm

  ​		即领域，相当于 datasource 数据源，SecurityManager 进行安全认证需要通过 Realm 获取用户权限数据，比如：如果用户身份数据在数据库，那么 Realm 就需要从数据库获取用户身份信息。不要把 Realm 理解成只是从数据源取数据，在 Realm 中还有认证授权校验的相关代码。

  ​		最基础的是Realm接口，CachingRealm负责缓存处理，AuthenticationRealm负责认证，AuthorizingRealm负责授权，通常自定义的realm继承AuthorizingRealm。

- SessionManager

  ​		即会话管理，Shrio 框架定义了一套会话管理，它不依赖 web 容器的 Session ，所以Shrio 可以使用在非 Web 应用上，也可以将分布式应用的会话集中在一点管理，此特性可使它实现单点登录。

- SessionDAO

  ​		即会话DAO,是对 Session 会话操作的一套接口，比如要将 Session 存储到数据库，可以通过 jdbc 将会话存储到数据库。

- CacheManager

  ​		即缓存管理，将用户权限数据存储在缓存，这样可以提高性能。

- Cryptografy

  ​		即密码管理，Shrio 提供了一套解密/加密的组件，方便开发。比如提供常用的散列、加/解密功能。

# 三. 过滤器

默认过滤器

- anon -- org.apache.shiro.web.filter.authc.AnonymousFilter
- authc -- org.apache.shiro.web.filter.authc.FormAuthenticationFilter
- authcBasic -- org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
- perms -- org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
- port -- org.apache.shiro.web.filter.authz.PortFilter
- rest -- org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
- roles -- org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
- ssl -- org.apache.shiro.web.filter.authz.SslFilter
- user -- org.apache.shiro.web.filter.authc.UserFilter
- logout -- org.apache.shiro.web.filter.authc.LogoutFilter