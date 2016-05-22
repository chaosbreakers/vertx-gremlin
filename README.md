# vertx-gremlin
> Gremlin server becomes Gremlin verticle

`Gremlin Server` 提供了一种在单图或多图的图实例中执行远程gremlin脚本的方式。更多了解Gremlin Server的相关内容请参考 [tinkerpop-gremlin server][1]

`vertx-gremlin`采用[Vertx][2]，充分利用vertx的优势，将Gremlin server变得更易使用、部署及维护。

## 添加依赖

```xml
<dependency>
  <groupId>com.monogram.metagraph</groupId>
  <artifactId>vertx-gremlin-client</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>

<dependency>
  <groupId>com.monogram.metagraph</groupId>
  <artifactId>vertx-gremlin-server</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

## 创建客户端

如果你只是想连接本地一个Gremlin Server,那么用下面这段代码就可以了。注意，建立连接的默认IP为`localhost`,默认port为`10180`。
```java
VertxGremlinClient client = VertxGremlinClient.create(vertx, config)
```

建立远程客户端连接只需在上面示例代码中添加两个参数就可以了。如下所示：

```java
VertxGremlinClient client = VertxGremlinClient.create(vertx, config, ip, host)
```

## vertx-gremlin-client API使用
### 脚本格式定义
脚本采用json作为传输的数据格式，其中至少要包含了两个字段`graphId`,`gremlinScript`，举例如下：
```json
{
  "graphId": "grapid-22554353",
  "gremlin": "g.V().count()",
}
```

也支持参数绑定的方式，如下所示：

```json
{
  "graphId": "grapid-22554353",
  "gremlinScript": "g.V().property('country',aaa).property('addr',bbb).valueMap()",
  "parambindings":[
      "aaa":"aaaValue",
      "bbb":"bbbValue"
  ]
}
```
如上所示的数据中，vertx-gremlin-server会将这些参数处理后，`gremlinScript`在被执行时的值会编程`g.V().property('country',"aaaValue").property('addr',"aaaValue").valueMap()`。注意这里的参数名`aaa`,`bbb`，要取名特别一点，最好不要取名为`gremlinScript`中出现的字符串。

### 执行一个脚本

```java
JsonObject script = new JsonObject();
script.put("graphId", "grapid-22554353");
script.put("gremlinScript", "g.V().property('country',aaa).property('addr',bbb).valueMap()");

vertxGremlinClient.execute( script, res -> {
      if (res.succeeded()) {
        JsonObject resultJson = res.result();
        System.out.println("执行结果为：" + resultJson);
      } else {
        res.cause().printStackTrace();
      }
});
```


  [1]: http://tinkerpop.apache.org/docs/3.2.1-SNAPSHOT/reference/#gremlin-server
  [2]: http://vertx.io/
