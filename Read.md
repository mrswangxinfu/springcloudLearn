application.yml是用户级的资源配置项
bootstrap.yml是系统级的，优先级更高

springCloud会创建一个“Bootstrap Context”, 作为Spring应用的“Application Context”父上下文。
初始化的时候“Bootstrap Context”负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的“Environment”。


"Bootstrap"属性具有高优先级，默认情况下，它们不会被本地配置覆盖，“Bootstrap Context”和“Application Context”有着不同的约定，
所以新增了一个“bootstrap.yml”文件，保证“Bootstrap Context”和“Application Context”配置的分离。


要将Client模块下的application.yml文件改为bootstrap.yml是很关键。
因为bootstrap.yml会比application.yml先加载

### spring stream （构建消息驱动微服务的框架，目前只支持rabbitmq和kafka）
* 屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型
例子： javaEE架构使用Rabbitmq,大数据平台使用Kafka，相互关联需要进行切换，比较麻烦.

原理：
  * 生产者--->springCloud stream(Source->Channel->Binder)--->MQ(Rabbit/kafka)--->
    springCloud stream(Binder->Channel->Sink)--->消费者
    
编码API和注解：
  * Middleware    中间件，目前只支持rabbitmq和kafka
  * Binder        是应用和消息中间件之间的封装
  * @Input        注解标识输入通道，通过该输入通道接收到的消息进入应用程序
  * @Output       注解标识输出通道，发布的消息将通过该通道离开应用程序
  * @StreamListener 监听队列，用于消费者的队列的消息接收
  * @EnableBinding 指定信道和channel和exchange绑定在一起
  
###springCloud Sleuth
* 当服务间调用链路过长过于繁杂，这时就需要一个服务链路跟踪。
SpringCloud Sleuth就是解决此问题。并且兼容zipkin

##springCloud Alibaba
###Nacos(Dynamic Naming and Configuration Service)
* 就是服务注册中心+配置中心的组合
* Nacos = Eureka + Config + Bus
* nacos的config由 Namespace、Group、DataID构成，Namespace默认是public
* sentinel实现熔断、降级、限流
* seata实现分布式事务控制
   * Transaction ID XID 全局唯一的事务ID
   * Transaction Coordinator(TC) 事务协调器
   * Transaction Manager(TM) 控制全局事务的边界
   * Resource Manager(RM) 控制分支事务