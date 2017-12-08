## Spring Boot Hazelcast Clustering

该项目展示了如何设置一个嵌入在Spring Boot的, 由Spring容器管理的集群Vert.x.

它由两个Verticle组成. 第一个启动一个HTTP服务器, 接收一个 query 参数, 并将他发送到总线上. 
第二个 Verticle 在事件总线监听, 在收到消息后进行应答, 并最终作为HTTP响应体发送到客户端.

## 代码

在 `io.vertx.examples.spring.clustering.hazelcast.VertxProducer` 类中, 
创建了一个集群的 Vert.x 实例, 它使用 Spring 管理的 `com.hazelcast.core.HazelcastInstance` 实例.

有多重配置方式, 最简单的是在 CLASSPATH 根中的 `hazelcast.xml` 文件. 

> `Hazelcast` 实例会在 Vert.x 实例之前创建, 并且在 Vert.x 销毁后销毁.

> 重要: Hazelcast 的关闭钩子应该被禁用. 要禁用它可以在 hazelcast.xml 文件中配置如下属性:

```xml
<properties>
    <!-- ... other properties ... -->
    <property name="hazelcast.shutdownhook.enabled">false</property>
</properties>
``` 