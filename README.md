# 说明
本模块为轻量化流程引擎,适用于嵌入到应用程序中实现流程的配置化,通过实现相应的接口，完成流程的定义
执行.
应用场景：流程引擎适用于基于状态变更的交易系统，同时也适用于存在流程化的系统，可方便复用流程原子级别的Action

# 接入说明
## 1.pom依赖
```xml
<dependency>
    <groupId>com.toolkits</groupId>
    <artifactId>workflow-engine-lite</artifactId>
    <version>1.0.0.20171123</version>
</dependency>

<!-- 对其他jar包的依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-tx</artifactId>
</dependency>
```

## 2. 日志配置
应用需要配置如下三个logger：
>* WORKFLOW-CORE     : 流程引擎核心日志
>* WORKFLOW-PROFILER : 流程引擎性能日志
>* WORKFLOW-CACGE    : 流程引擎缓存日志


## 3. 流程设计 && 编码实现
### 3.1 流程统一入口指令定义
对于进入流程引擎的业务指令，需统一实现AbstractInstruction抽象类, 每个进入流程引擎的指令必须包含
流程Id、原始状态、目标状态，这样才能保证指令匹配到正确的process并执行相关的action， 自定义指令如下：
```java
/**
 * demo业务Instruction
 * @author XiaokaiSun
 * @version $Id: DemoInstruction.java, v 0.1 2017-12-06 10:30 XiaokaiSun Exp $$
 */
public class DemoInstruction extends AbstractInstruction {
    
    /** 交易信息或其他，此处仅为参考 */
    private Object transactionInfo;

    /**
     * Getter method for property <tt>transactionInfo<tt>.
     *
     * @return property value of transactionInfo
     */
    public Object getTransactionInfo() {
        return transactionInfo;
    }

    /**
     * Setter method for property <tt>transactionInfo<tt>.
     *
     * @param transactionInfo value to be assigned to property transactionInfo
     */
    public void setTransactionInfo(Object transactionInfo) {
        this.transactionInfo = transactionInfo;
    }
}
```

### 3.2 流程设计
流程引擎需要自定义流程的运转流程，本质上就是基于业务的状态机的跳转流程，具体的配置如下demo，
其中要定义每个状态跳转执行的action，与执行成功或失败的router,如下下事例：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<workFlowConfig id="demo" desc="demo流程定义">

    <propeties>
        <key>value</key>
    </propeties>

    <process-list>
        <process id="PRO-NULL-AC" sourceStatus="NULL" destStatus="AC" router="正常业务路由器" failrouter="业务失败路由"
                 desc="请求受理流程">
            <action-list>
                <action seq="1" ref="actionBeanName1" desc="操作描述1"/>
                <action seq="2" ref="actionBeanName2" desc="操作描述2"/>
                <action seq="3" ref="actionBeanName3" desc="操作描述3"/>
                <action seq="4" ref="actionBeanName4" desc="操作描述4"/>
                <action seq="5" ref="actionBeanName5" desc="操作描述5"/>
            </action-list>
        </process>

        <process id="PRO-AC-SM" sourceStatus="AC" destStatus="SM" next="下一个Process" failrouter="业务失败路由" desc="请求受理流程">
            <action-list>
                <action seq="1" ref="actionBeanName" desc="操作描述1"/>
                <action seq="2" ref="updateDbAction" isInTrans="true" desc="操作描述2"/>
            </action-list>
        </process>
    </process-list>
</workFlowConfig>
```

### 3.3 action实现
每个业务action需实现 BusinessAction 接口来完成相关流程的执行，具体demo如下:
```java
/**
 * 自定义action
 * @author XiaokaiSun
 * @version $Id: DemoAction.java, v 0.1 2017-12-06 10:29 XiaokaiSun Exp $$
 */
public class DemoAction implements BusinessAction {

    /**
     * 执行Action操作
     *
     * @param context
     * @throws BusinessActionException
     */
    @Override
    public void process(WorkFlowContext context) throws BusinessActionException {
        /** 获取自定义的指令 */
        DemoInstruction demoInstruction = context.getInstruction(DemoInstruction.class);
        
        /** 执行相关操作, 自定义实现 */
        doBusinessAction();
        
    }

    /**
     * 执行指令
     */
    private void doBusinessAction(){
        
    }
}
```

### 3.4 router实现
对于每个router需继承 AbstractProcessRouter 实现相关的router逻辑，具体demo如下：
```java
/**
 * 事例router
 * @author XiaokaiSun
 * @version $Id: DemoRouter.java, v 0.1 2017-12-06 10:38 XiaokaiSun Exp $$
 */
public class DemoRouter extends AbstractProcessRouter{

    /**
     * 抽象业务处理逻辑
     *
     * @param workFlowContext
     * @return
     */
    @Override
    public WorkFlowContext doBusinessProcess(WorkFlowContext workFlowContext) {
        /** 执行相关操作 */
        return workFlowContext;
    }

    /**
     * 设置路由原始状态
     *
     * @param workFlowContext
     * @return
     */
    @Override
    public WorkFlowContext setSourceStatus(WorkFlowContext workFlowContext) {
        workFlowContext.getInstruction().setSourceStatus("NULL");
        return workFlowContext;
    }

    /**
     * 设置路由目标状态
     *
     * @param workFlowContext
     * @return
     */
    @Override
    public WorkFlowContext setDestStatus(WorkFlowContext workFlowContext) {
        workFlowContext.getInstruction().setDestStatus("FA");
        return workFlowContext;
    }
}
```

### 3.5 spi实现
在定义好流程，并实现相关的action与router后, 大家可能会有疑问，组件怎么感知到外部的配置与定义,那接下来就要实现
组件定义的spi了，组件定义了两个spi，分别用于获取流程xml配置与action与router具体的bean,如下:

#### WorkFlowTemplateFetcher
功能: 获取WorkFlow配置的接口, 返回的内容用类 WorkFlowFethchResult 封装
说明：返回的WorkFlow配置可有两种实现方式
>* 直接返回WorkFlow配置xml的String形式, 此时需setLocalPath(false), resultList中为配置文件列表
>* 直接返回配置文件在classpath的路径, 此时 isLocalPath=true, 同时resultList为一个路径列表,每一条的形式如下
```
classpath:****.xml
```

#### ComponentFetcher
功能：获取workFlow执行过程中各个Action和router的实现的bean
说明：实现ComponentFetcher的接口即可, 若获取不到直接返回null, 组件初始化失败，应用系统可通过applicationContext
来实现bean的管理，


## 4. 接入规约
为了更方便的接入系统, 我们希望轻量化流程引擎的使用者能按照如下规约进行相关的配置

>* 1. 状态流程统一用大写英文字母
>* 2. 流程定义的时候, next与successRouter只能二选一，若process存在next属性，则我们会忽略掉successRouter的配置
>* 3. spi的实现我们默认使用spring的di方式实现,ComponentFetcher 的实现 beanName需定义为：componentFetcher
>* 4. WorkFlowTemplateFetcher 的beanName 定义为：workFlowTemplateFetcher

### 4.1 Exception标准化
对于继承实现 BusinessAction ProcessRouter 接口的bean, 可统一采用WorkFlow封装的 BusinessActionException, 
出现异常中断流程可抛出该类型的异常，应用只需要实现错误的枚举定义就好，具体如下:
>* 继承 ProcessBaseEnum 接口
>* 定义对应的业务错误码, 可参考：WorkFlowErrorCode 的实现

## 5. 组件使用
应用使用流程组件，可直接注入使用，如下demo:
```java
public class Demo{
    
    /** 流程引擎 */
    @javax.annotation.Resource(name = "workFlowEngine")
    private WorkFlowEngine workFlowEngine;
    
    /**
    * 进入流程
    */
    public void doProcess(){
        Instruction instruction = new YourInstruction();
        instruction.setWorkFlowId("xx");
        instruction.setSourceStatus("**");
        instruction.setDestStatus("**");
        
        this.workFlowEngine.launch(instruction);
    }
}
```

## 6. demo
测试用例详情可见：
```java
com.toolkits.workflow.integrationtest.testcase.WorkFlowTest
```








